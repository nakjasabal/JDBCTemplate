package springboard.service;

import org.springframework.ui.Model;

/*
게시판은 목록, 쓰기, 읽기 등이 하나의 세트로 구성되므로 
해당 클래스들을 하나로 묶어주는것이 좋다. 해당 인터페이스는 
각 클래스의 부모클래스의 역할을 위해 생성한다. 
인터페이스는 객체를 생성할수 없지만, 구현(상속)이 가능하고 오버라이딩, 
참조변수 등의 기능을 사용할 수 있다. 
 */
public interface IBoardService {

	//추상메서드 : 하위 클래스에서는 무조건 오버라이딩 해야한다. 
	void execute(Model model); 
}
