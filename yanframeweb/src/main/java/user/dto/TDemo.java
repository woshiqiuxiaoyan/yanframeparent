package user.dto;

import org.apache.ibatis.type.Alias;
import org.hibernate.validator.constraints.Length;
import org.joda.time.DateTime;
import user.controller.validation.VaildatorGroup1;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * Created by t on 2017/8/16.
 */
public class TDemo implements Serializable {


    private int id;

    @NotNull(message = "{tdemo.userName.null}")
    @Length(min = 5, max = 10, message = "{tdemo.userName.size}")
    private String userName;

    @Min(value = 10,message = "{tdemo.userName.age.min}",groups = {VaildatorGroup1.class})
    private int age;

    private DateTime createTime;

    private DateTime updateTime;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public DateTime getCreateTime() {
        return createTime;
    }

    public void setCreateTime(DateTime createTime) {
        this.createTime = createTime;
    }

    public DateTime getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(DateTime updateTime) {
        this.updateTime = updateTime;
    }

    @Override
    public String toString() {
        return "TDemo{" +
                "id=" + id +
                ", userName='" + userName + '\'' +
                ", age=" + age +
                ", createTime=" + createTime +
                ", updateTime=" + updateTime +
                '}';
    }
}
