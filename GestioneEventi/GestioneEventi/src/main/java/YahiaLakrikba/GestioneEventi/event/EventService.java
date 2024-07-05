package YahiaLakrikba.GestioneEventi.event;

import YahiaLakrikba.GestioneEventi.exeptions.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class EventService {

    @Autowired
    private EventRepository eventRepository;

    public Page<Event> getEvent(int page, int size, String orderBy) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(orderBy));
        return eventRepository.findAll(pageable);
    }

    public Event findById(long id) throws NotFoundException {
        return eventRepository.findById(id).orElseThrow(() -> new NotFoundException(id));
    }

    public void deleteById(long id) throws NotFoundException {
        Event found = findById(id);
        eventRepository.delete(found);
    }

    public Event updateEvent(long id, Event body) throws NotFoundException {
        Event found = findById(id);
        found.setTitle(body.getTitle());
        found.setDescription(body.getDescription());
        found.setLocation(body.getLocation());
        found.setSeatsAvailable(body.getSeatsAvailable());
        return eventRepository.save(found);
    }

    public Event createEvent(Event evento) {
        return eventRepository.save(evento);
    }

}
