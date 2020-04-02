package model;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "USER", schema = "RING")
public class User {
    private String first;
    private String last;
    private String email;
    private String accountId;

    public String getFirst() {
        return first;
    }

    public void setFirst(String first) {
        this.first = first;
    }

    public String getLast() {
        return last;
    }

    public void setLast(String last) {
        this.last = last;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAccountId() {
        return accountId;
    }

    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return first.equals(user.first) &&
                last.equals(user.last) &&
                email.equals(user.email) &&
                accountId.equals(user.accountId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(first, last, email, accountId);
    }

    @Override
    public String toString() {
        return "User{" +
                "first='" + first + '\'' +
                ", last='" + last + '\'' +
                ", email='" + email + '\'' +
                ", accountId='" + accountId + '\'' +
                '}';
    }
}
