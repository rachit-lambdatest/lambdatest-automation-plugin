package com.lambdatest.jenkins.freestyle.api.operatingsystem;

import java.util.List;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "os"
})
public class OsList {

    @JsonProperty("os")
    private List<Os> os = null;

    @JsonProperty("os")
    public List<Os> getOs() {
        return os;
    }

    @JsonProperty("os")
    public void setOs(List<Os> os) {
        this.os = os;
    }

}