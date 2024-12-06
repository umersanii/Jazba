package jazba.controller.misc;

public class RegisterUserCommand {
    private final RegistrationService service;
    private final String username;
    private final String email;
    private final String password;
    private final int height;
    private final int weight;
    private final int age;
    private final String fitnessLevel;

    public RegisterUserCommand(RegistrationService service, String username, String email, String password, int height, int weight, int age, String fitnessLevel) {
        this.service = service;
        this.username = username;
        this.email = email;
        this.password = password;
        this.height = height;
        this.weight = weight;
        this.age = age;
        this.fitnessLevel = fitnessLevel;
    }

    public int execute() {
        return service.registerUser(username, email, password, height, weight, age, fitnessLevel);
    }
}