package com.restApi.journalApp.Entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
@Getter
@Setter
public class Weather {
    private Current current;
    @Getter
    @Setter
    public class Current {
        public int temperature;
        public String wind_dir;
        public int pressure;
    }


}
