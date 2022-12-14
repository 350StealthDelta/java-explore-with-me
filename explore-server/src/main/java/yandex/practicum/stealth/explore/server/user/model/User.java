package yandex.practicum.stealth.explore.server.user.model;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.Size;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@ToString
@EqualsAndHashCode(of = "id")
@Entity
@Table(name = "users", schema = "public")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "u_id")
    private Long id;

    @Column(name = "u_email", nullable = false, unique = true)
    private String email;

    @Column(name = "u_name", nullable = false)
    @Size(min = 1, max = 255)
    private String name;
}
