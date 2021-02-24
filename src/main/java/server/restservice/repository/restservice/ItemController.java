package server.restservice.repository.restservice;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/items")
public class ItemController {

    @GetMapping(value = "/")
    public Item[] getAllItems() {
        Item[] output = ItemFetcher.getAllItems();
        return output;
    }

    @GetMapping(value = "/{id}")
    public Item getItem(@PathVariable int id) {
        return ItemFetcher.getItemById(id);
    }
}