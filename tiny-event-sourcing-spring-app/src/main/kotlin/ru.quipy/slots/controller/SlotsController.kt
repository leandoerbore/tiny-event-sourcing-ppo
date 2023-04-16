package ru.quipy.slots.controller

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import ru.quipy.core.EventSourcingService
import ru.quipy.slots.api.*
import ru.quipy.slots.dto.*
import ru.quipy.slots.logic.*
import ru.quipy.slots.logic.SlotsAggregateState.Status
import ru.quipy.slots.service.SlotsMongo
import ru.quipy.slots.service.SlotsRepository
import java.time.Instant
import java.time.ZoneOffset
import java.util.*


@RestController()
@RequestMapping("/slots")
class SlotsController (
    val slotsESService: EventSourcingService<UUID, SlotsAggregate, SlotsAggregateState>,
    val slotsRepository: SlotsRepository,
){

    @PostMapping()
    fun createItem(@RequestBody request: CreateSlotDTO): ResponseEntity<Any> {
        val now = Instant.now()

        if (request.time < now){ // слоты задним числом запрещены
            return ResponseEntity<Any>("Error: this time is gone", HttpStatus.UNPROCESSABLE_ENTITY)
        }
        if (slotsRepository.findOneByTime(request.time) != null) { // слоты с одинаковым временем запрещены
            return ResponseEntity<Any>("Error: created yet", HttpStatus.CONFLICT)
        }
        if (request.time.atZone(ZoneOffset.UTC).minute % 15 != 0) { // проверка на кратность времени 15 минутам
            return ResponseEntity<Any>(null, HttpStatus.UNPROCESSABLE_ENTITY)
        }
        val slot = slotsESService.create{ it.createSlot(
                time = request.time,
                status = request.status
        )}

        return ResponseEntity.ok( slotsRepository.save(SlotsMongo(
            aggregateId = slot.slotId,
            time = slot.time,
            status =  slot.status
        )))
    }

    @PatchMapping("/status")
    fun updateSlotStatus(@RequestBody request: UpdateSlotStatusDTO): ResponseEntity<Any> {
        if (slotsRepository.findOneByAggregateId(request.id) == null) {
            return ResponseEntity<Any>(null, HttpStatus.BAD_REQUEST)
        }

        return ResponseEntity.ok(slotsESService.update(request.id){it.updateSlotStatus(id = request.id, request.status)})
    }

    @GetMapping("/available")
    fun getAvailableSlots(): ResponseEntity<Any> {
        val result = ArrayList<GetAvailableSlotsDTO>()
        for (slot in slotsRepository.findAllByStatusAndTimeAfter(Status.FREE, Instant.now())) {
            result.add(
                    GetAvailableSlotsDTO(
                            time = slotsESService.getState(slot.aggregateId)!!.getTime(),
                            status = slotsESService.getState(slot.aggregateId)!!.getStatus(),
                            id = slotsESService.getState(slot.aggregateId)!!.getId()
                    )
            )
        }

        return ResponseEntity.ok(result)
    }


    @DeleteMapping("/remove")
    fun removeSlot(@RequestBody request: RemoveSlotDTO): ResponseEntity<Any> {
        if (slotsRepository.findOneByAggregateId(request.id) == null) {
            return ResponseEntity<Any>(null, HttpStatus.BAD_REQUEST)
        }
        return ResponseEntity.ok(slotsESService.update(request.id){it.removeSlot(id = request.id)})
    }
}