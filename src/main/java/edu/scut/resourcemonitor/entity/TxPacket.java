package edu.scut.resourcemonitor.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.Hibernate;
import org.hibernate.annotations.CreationTimestamp;

import java.sql.Timestamp;
import java.util.Objects;


@Getter
@Setter
@ToString
@RequiredArgsConstructor
@Table(name = "user")
@Entity
public class TxPacket {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    String IFName;
    private String protocol;
    private String src;
    private String dest;
    private Integer srcPort;
    private Integer destPort;
    @CreationTimestamp
    private Timestamp time;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        TxPacket packet = (TxPacket) o;
        return getId() != null && Objects.equals(getId(), packet.getId());
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
