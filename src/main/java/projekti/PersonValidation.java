package projekti;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class PersonValidation {
    
    @NotEmpty
    private String firstName;
    
    @NotEmpty
    private String lastName;

    @NotEmpty
    private String userUrl;

    @NotEmpty
    @Size(min = 3, max = 15)
    private String username;

    @NotEmpty
    @Size(min = 5, max = 15)
    private String password;

}