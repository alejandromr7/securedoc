package securedoc.entity;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.util.AlternativeJdkIdGenerator;

import java.time.LocalDateTime;

@Getter
@Setter
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
@JsonIgnoreProperties(value = {"createdAt", "updatedAt"}, allowGetters = true)
public abstract class Auditable {
    @Id
    @SequenceGenerator(name = "primary_key_seq", sequenceName = "primary_key_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "primary_key_seq")
    @Column(name = "id", updatable = false)

    private Long id;
    private String referencedId = new AlternativeJdkIdGenerator().generateId().toString();

    @NotNull
    private Long createdBy;
    private Long updatedBy;

    @NotNull
    @CreatedDate
    @Column(name = "updated_at", updatable = false, nullable = false)
    private LocalDateTime updatedAt;

    @NotNull
    @CreatedDate
    @Column(name = "created_at", updatable = false, nullable = false)
    private LocalDateTime createdAt;

    @PrePersist
    public void prePersist() {
        var userId = 1L;
        if (userId == null) throw new APIException("Cannot persist entity");
        setCreatedBy(userId);
        setUpdatedBy(userId);
        setUpdatedAt(LocalDateTime.now());
        setCreatedAt(LocalDateTime.now());
    }

    @PreUpdate
    public void prePersist() {
        var userId = 1L;
        if (userId == null) throw new APIException("Cannot persist entity");
        setUpdatedAt(LocalDateTime.now());
        setCreatedAt(LocalDateTime.now());
    }

}
