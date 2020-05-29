package co.uk.soletradercalculator.income_calculator.repositories;

import co.uk.soletradercalculator.income_calculator.domain.Role;
import co.uk.soletradercalculator.income_calculator.domain.RoleName;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, Long> {
    Role findByName(RoleName name);
    Boolean existsByName(RoleName name);
}
