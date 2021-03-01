package com.intempt.demo.quartzdemo.pull;

import com.intempt.demo.quartzdemo.timers.JobInfo;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/timer")
@RequiredArgsConstructor
public class PullSourceController {

    private final PullSourceService service;

    @PostMapping("/1")
    public void first() {
        service.addJob1();
    }

    @PostMapping("/2")
    public void second() {
        service.addJob2();
    }

    @PutMapping("/1")
    public void upd_first() {
        service.update1();
    }

    @PutMapping("/2")
    public void upd_second() {
        service.update2();
    }


    @GetMapping("/all")
    public List<JobInfo> getAllRunningTimers() {
        return service.getAll();
    }

    @GetMapping("/{id}")
    public JobInfo get(@PathVariable String id) {
        return service.get(id);
    }

    @DeleteMapping("/{id}")
    public Boolean deleteTimer(@PathVariable String id) {
        return service.delete(id);
    }
}
