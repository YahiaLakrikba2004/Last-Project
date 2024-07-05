package YahiaLakrikba.GestioneEventi.event;

import YahiaLakrikba.GestioneEventi.exeptions.BadRequestExeption;
import YahiaLakrikba.GestioneEventi.exeptions.NotFoundException;
import YahiaLakrikba.GestioneEventi.user.User;
import org.springframework.data.domain.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/events")
public class EventController {


    @Autowired
    private EventService eventService;

    //Endpoint eventi impaginati
    @GetMapping
    public ResponseEntity<Page<Event>> getEvents(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "date") String orderBy
    ) {
        Page<Event> events = eventService.getEvent(page, size, orderBy);
        return ResponseEntity.ok(events);
    }


    //Endpoint creazione nuovo evento
    @PostMapping
    public ResponseEntity<Event> createEvent(@RequestBody Event event) {
        try {
            Event newEvent = eventService.createEvent(event);
            return ResponseEntity.status(HttpStatus.CREATED).body(newEvent);

        }catch (BadRequestExeption e) {
            return ResponseEntity.badRequest().build();
        }
    }

    //Endpoint cercare utente con id
    @GetMapping("/{id}")
    public ResponseEntity<Event> getEventById(@PathVariable long id) {
        try {
            Event event = eventService.findById(id);
            return ResponseEntity.ok(event);
        } catch (NotFoundException e) {
            return  ResponseEntity.notFound().build();
        }
    }

    //Endpoint aggiornamento utente
    @PutMapping("/{id}")
    public ResponseEntity<Event> updateEvent(@PathVariable long id, @RequestBody Event event){
        try {
            Event updateEvent = eventService.updateEvent(id, event);
            return ResponseEntity.ok(updateEvent);
        }catch (NotFoundException e){
            return ResponseEntity.notFound().build();
        }
    }

    //Endpoint eliminazione utente
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteEvent(@PathVariable long id) {
        try {
            eventService.deleteById(id);
            return ResponseEntity.noContent().build();
        }catch (NotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

}
