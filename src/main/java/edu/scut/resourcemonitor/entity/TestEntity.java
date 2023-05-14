package edu.scut.resourcemonitor.entity;

import lombok.Data;
import lombok.ToString;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
@ToString
public class TestEntity {

    String id;
    String name = "this is a test Entity";
    TestParent parent;
}
