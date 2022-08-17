package Service;

import java.io.File;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

public class Audio {
	private Clip clip;
	private File audioFile;
	private AudioInputStream audioInputStream;
	private boolean isLoop;

	public Audio() {
	}

	public Audio(String pathName, boolean isLoop) {
		try {
			clip = AudioSystem.getClip(); // 오디오 재생을 위한 클립 받기
			audioFile = new File(pathName); // 파일 객체 생성
			audioInputStream = AudioSystem.getAudioInputStream(audioFile);
			// 경로명에 있는 파일로 부터 오디오 입력 스트림을 가져오기
			clip.open(audioInputStream);

			} catch (Exception e) {
				e.printStackTrace();
			}
		}

	public void start() {
		clip.setFramePosition(0); // 클립을 파일의 처음에 놓고 재생
		clip.start();
		if(isLoop) clip.loop(clip.LOOP_CONTINUOUSLY); // 반복 재생 코드
	}
	
	
	public void stop() {
		clip.stop();
		
	}
	
	
	
}
