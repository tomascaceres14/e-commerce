package com.tomasdev.akhanta.service;


import com.tomasdev.akhanta.model.Activity;

import java.util.List;

public interface ActivityService extends iService<Activity>{

    List<List<String>> generateMtx();
}
