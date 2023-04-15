package ru.quipy.slots.controller

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import ru.quipy.slots.api.*
import ru.quipy.slots.dto.*
import ru.quipy.slots.logic.*
import ru.quipy.slots.service.SlotsRepository
import ru.quipy.slots.service.SlotsMongo
import ru.quipy.core.EventSourcingService
import java.util.*
import java.text.SimpleDateFormat
import java.util.concurrent.locks.ReentrantLock
import kotlin.collections.ArrayList
import java.lang.IllegalStateException
import ru.quipy.slots.logic.SlotsAggregateState.Status



@RestController()
@RequestMapping("/slots")
class SlotsController (
    val slotsESService: EventSourcingService<UUID, SlotsAggregate, SlotsAggregateState>,
    val slotsRepository: SlotsRepository,
){

    @PostMapping()
    fun createItem(@RequestBody request: CreateSlotDTO): Any {
        val dataFormatter = SimpleDateFormat("yyyy-MM-dd'T'HH:mm")
        val now = Date();
        if (dataFormatter.parse(request.time) < now){
            return ResponseEntity<Any>("Error: this time is gone", HttpStatus.UNPROCESSABLE_ENTITY)
        }
        if (slotsRepository.findOneByTime(dataFormatter.parse(request.time)) != null) {
            return ResponseEntity<Any>("Error: created yet", HttpStatus.CONFLICT)
        }
        val slot = slotsESService.create{ it.createSlot(
                time = request.time,
                status = request.status
        )}

        return slotsRepository.save(SlotsMongo(
                time = slot.time,
                status =  slot.status,
                aggregateId = slot.slotId
        ))
    }

    @PatchMapping("/status/{id}")
    fun updateSlotStatus(@PathVariable id: UUID, @RequestBody request: UpdateSlotStatusDTO): Any {
        if (slotsRepository.findOneByAggregateId(id) == null) {
            return ResponseEntity<Any>(null, HttpStatus.BAD_REQUEST)
        }
        if (request.status == slotsRepository.findOneByAggregateId(id)!!.status.status){
            return ResponseEntity<Any>(null, HttpStatus.CONFLICT)
        }
        return slotsESService.update(id){it.updateSlotStatus(id = id, request.status)}
    }

    @GetMapping
    fun getSlots(): Any {
        val result = ArrayList<GetAvailableSlotsDTO>()
        for (slot in slotsRepository.findAll()) {
            result.add(
                    GetAvailableSlotsDTO(
                            time = slotsESService.getState(slot.aggregateId)!!.getTime(),
                            status = slotsESService.getState(slot.aggregateId)!!.getStatus(),
                            id = slotsESService.getState(slot.aggregateId)!!.getId()
                    )
            )
        }

        return result
    }
}