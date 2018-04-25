package cc.shencai.commonlibrary.beans;

import java.util.List;

/**
 * Created by yss on 2017/9/14
 *
 * @version 1.0.0
 */
public class ThreeListBean {
	String title1 = "";
	String title2 = "";
	String title3 = "";

	private List<FirstBean> firstBeens;

	public List<FirstBean> getFirstBeens() {
		return firstBeens;
	}

	public void setFirstBeens(List<FirstBean> firstBeens) {
		this.firstBeens = firstBeens;
	}

	public String getTitle1() {
		return title1;
	}

	public void setTitle1(String title1) {
		this.title1 = title1;
	}

	public String getTitle2() {
		return title2;
	}

	public void setTitle2(String title2) {
		this.title2 = title2;
	}

	public String getTitle3() {
		return title3;
	}

	public void setTitle3(String title3) {
		this.title3 = title3;
	}

	public static class FirstBean{
		String firstName = "";
		private List<SecondBean> secondBeens;

		public String getFirstName() {
			return firstName;
		}

		public void setFirstName(String firstName) {
			this.firstName = firstName;
		}

		public List<SecondBean> getSecondBeens() {
			return secondBeens;
		}

		public void setSecondBeens(List<SecondBean> secondBeens) {
			this.secondBeens = secondBeens;
		}
	}


	public static class SecondBean {
		String SecondName = "";
		private List<ThirdBean> thirdBeans;

		public String getSecondName() {
			return SecondName;
		}

		public void setSecondName(String secondName) {
			SecondName = secondName;
		}

		public List<ThirdBean> getThirdBeans() {
			return thirdBeans;
		}

		public void setThirdBeans(List<ThirdBean> thirdBeans) {
			this.thirdBeans = thirdBeans;
		}
	}

	public static class ThirdBean {
		String ThirdName = "";

		public String getThirdName() {
			return ThirdName;
		}

		public void setThirdName(String thirdName) {
			ThirdName = thirdName;
		}
	}
}
