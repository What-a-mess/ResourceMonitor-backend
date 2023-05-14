package edu.scut.resourcemonitor.entity;

import lombok.Data;
import lombok.ToString;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
@ToString
public class TestChild extends TestParent {
    String src = "0000";
    String dest = "1234";
}
