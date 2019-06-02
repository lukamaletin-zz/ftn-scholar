package io.github.lukamaletin.ftnscholar.dto;

import org.camunda.bpm.engine.rest.dto.VariableValueDto;

import java.util.HashMap;
import java.util.Map;

public class VariablesDto extends HashMap<String, VariableValueDto> {

    public VariablesDto() {
    }

    public VariablesDto(Map<String, Object> variables) {
        for (Map.Entry<String, Object> variable : variables.entrySet()) {
            final VariableValueDto variableValueDto = new VariableValueDto();
            variableValueDto.setValue(variable.getValue());
            variableValueDto.setType("String");
            put(variable.getKey(), variableValueDto);
        }
    }
}
