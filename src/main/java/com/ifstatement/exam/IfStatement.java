package com.ifstatement.exam;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data

public class IfStatement {

    String type;
    List<Conditions> conditions = new ArrayList<>();
    IfStatement elseStatement;
    boolean thenResult;
}
