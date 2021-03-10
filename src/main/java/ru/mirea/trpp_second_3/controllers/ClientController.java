package ru.mirea.trpp_second_3.controllers;

import com.opencsv.bean.CsvToBeanBuilder;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import ru.mirea.trpp_second_3.entity.Client;

import java.io.InputStreamReader;
import java.util.List;
import java.util.Optional;

/** Контроллер для работы с клиентами. */
@Controller("/client")
public class ClientController {

    /** Список клиентов. */
    private final List<Client> clientList;

    /** Конструктор. */
    public ClientController() {
        clientList = new CsvToBeanBuilder<Client>(new InputStreamReader(this.getClass().getResourceAsStream("/MOCK_DATA.csv"))).withType(Client.class).build().parse();
    }

    /**
     * Получить список клиентов.
     * @return список клиентов
     */
    @Get()
    public HttpResponse<List<Client>> getClient() {
        return HttpResponse.ok(clientList);
    }

    /**
     * Найти клиента по идентификатору.
     * @param id идентификатор клиента
     * @return Клиент, если есть, иначе 404 ошибка
     */
    @Get("/{id}")
    public HttpResponse<Client> findById(Long id) {
        Optional<Client> result = clientList.stream().filter(it -> it.getId().equals(id)).findAny();
        if (result.isPresent()) {
            return HttpResponse.ok(result.get());
        } else {
            return HttpResponse.notFound();
        }
    }
}
