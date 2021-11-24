package gruppe8.nebula.SignupController;

/**
 * This record is equivalent to the following class
 *
 *  <pre>
 *  public class SignupRequest {
 *      private String fullName;
 *      private String email;
 *      private String password;
 *
 *      public SignupRequest(String fullName, String email, String password) {
 *      }
 *
 *      public fullName() {
 *          return this.fullName;
 *      }
 *
 *      public email() {
 *          return this.email;
 *      }
 *
 *      public password() {
 *          return this.password;
 *      }
 *  }
 *  </pre>
 *
 */

public record SignupRequest(String fullName, String email, String password) {
}