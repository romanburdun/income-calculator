package co.uk.soletradercalculator.income_calculator.api.v1.mappers;

import co.uk.soletradercalculator.income_calculator.api.v1.model.UserDTO;
import co.uk.soletradercalculator.income_calculator.domain.User;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface UserMapper {
    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

    User userDtoToUser(UserDTO userDTO);
    UserDTO userToUserDTO(User user);
}
