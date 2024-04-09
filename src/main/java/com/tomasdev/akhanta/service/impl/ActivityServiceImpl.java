package com.tomasdev.akhanta.service.impl;

import com.tomasdev.akhanta.exceptions.ResourceNotFoundException;
import com.tomasdev.akhanta.model.Activity;
import com.tomasdev.akhanta.repository.ActivityRepository;
import com.tomasdev.akhanta.service.ActivityService;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
@AllArgsConstructor
public class ActivityServiceImpl implements ActivityService {

    ActivityRepository repository;
    private final ModelMapper mapper;

    @Override
    public Activity save(Activity req) {
        return repository.save(req);
    }

    @Override
    public Page<Activity> findAll(int page) {
        PageRequest pageable = PageRequest.of(page, 10);
        return repository.findAll(pageable);
    }

    @Override
    public Activity findById(String id) {
        return repository.findById(id).orElseThrow(() -> new ResourceNotFoundException(STR."Activity id \{id} doesn't exists"));
    }

    @Override
    public Activity updateById(String id, Activity req) {
        Activity activityDB = findById(id);
        mapper.map(req, activityDB);
        activityDB.setId(id);
        return repository.save(activityDB);
    }

    @Override
    public void deleteById(String id) {
        if (!repository.existsById(id)) {
            throw new ResourceNotFoundException(STR."Activity id \{id} doesn't exists");
        }
        repository.deleteById(id);
    }

    @Override
    public List<List<String>> generateMtx() {
        List<List<String>> matrix = new ArrayList<>();
        List<String> headers = new ArrayList<>(Arrays.asList("Hora", "Lunes", "Martes", "Miercoles", "Jueves", "Viernes"));
        matrix.add(headers);

        List<Activity> activities = repository.findAll();

        for (Activity activity : activities) {

            List<Activity.dayHour> dayHours = activity.getDayHourList();

            for (Activity.dayHour dayHour : dayHours) {

                List<String> line = new ArrayList<>(Arrays.asList(dayHour.getHour(), "", "", "", "", ""));

                for (String day : dayHour.getDays()) {
                    switch (day) {
                        case "Lunes" -> line.set(1, activity.getName());
                        case "Martes" -> line.set(2, activity.getName());
                        case "Miercoles" -> line.set(3, activity.getName());
                        case "Jueves" -> line.set(4, activity.getName());
                        case "Viernes" -> line.set(5, activity.getName());
                    }
                }

                matrix.add(line);
            }
        }

        return matrix;
    }
}
