package cc.shencai.commonlibrary.beans;

import java.io.Serializable;

/**
 * Created by yss on 2017/9/14
 *
 * @version 1.0.0
 */
public class ThreeListResultBean implements Serializable {
	String FirstName = "";
	String SecondName = "";
	String ThirdName = "";

	public String getFirstName() {
		return FirstName;
	}

	public void setFirstName(String firstName) {
		FirstName = firstName;
	}

	public String getSecondName() {
		return SecondName;
	}

	public void setSecondName(String secondName) {
		SecondName = secondName;
	}

	public String getThirdName() {
		return ThirdName;
	}

	public void setThirdName(String thirdName) {
		ThirdName = thirdName;
	}
}
