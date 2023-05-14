package edu.scut.resourcemonitor.entity;

import lombok.Data;
import lombok.ToString;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
@ToString
public class TestParent {
    int length = 65534;
}
