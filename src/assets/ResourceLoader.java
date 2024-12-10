package assets;

import javafx.scene.image.Image;
import javafx.scene.media.AudioClip;
import java.util.HashMap;
import java.util.Map;

public class ResourceLoader {
	private static final Map<String, Image> IMAGES = new HashMap<>();
    private static final Map<String, AudioClip> AUDIO_CLIPS = new HashMap<>();

    // This method loads all resources at once
    public static void loadResources() {
    	loadImage("HomePageImage", "HomePage.png");
    	loadImage("BackGroundImage", "background.png");
    	loadImage("Logo", "Logo.png");
    	
    	loadImage("BasePlantImage", "baseplant.png");
    	loadImage("SuperPlantImage", "superplant.png" );
    	loadImage("TrapPlantImage", "trapplant.png" );
    	loadImage("BulletImage", "bullet.png");
    	
    	loadImage("Kappa_Walk1Image", "kappa_walk1.png");
    	loadImage("Kappa_Walk2Image", "kappa_walk2.png");
    	loadImage("Kappa_DeadImage", "kappa_dead.png");
    	loadImage("Kappa_Hit1Image", "kappa_hit1.png");
    	loadImage("Kappa_Hit2Image", "kappa_hit2.png");
    	loadImage("Kappa_Attack1Image", "kappa_attack1.png");
    	loadImage("Kappa_Attack2Image", "kappa_attack2.png");
    	loadImage("Kappa_Attack3Image", "kappa_attack3.png");
    	loadImage("Kappa_Attack4Image", "kappa_attack4.png");
    	
    	loadImage("Berserker_Walk1Image", "berserker_walk1.png");
    	loadImage("Berserker_Walk2Image", "berserker_walk2.png");
    	loadImage("Berserker_DeadImage", "berserker_dead.png");
    	loadImage("Berserker_Hit1Image", "berserker_hit1.png");
    	loadImage("Berserker_Hit2Image", "berserker_hit2.png");
    	loadImage("Berserker_Attack1Image", "berserker_attack1.png");
    	loadImage("Berserker_Attack2Image", "berserker_attack2.png");
    	loadImage("Berserker_Attack3Image", "berserker_attack3.png");
    	loadImage("Berserker_Attack4Image", "berserker_attack4.png");
    	
    	loadImage("Wukong_Walk1Image", "wukong_walk1.png");
    	loadImage("Wukong_Walk2Image", "wukong_walk2.png");
    	loadImage("Wukong_DeadImage", "wukong_dead.png");
    	loadImage("Wukong_Hit1Image", "wukong_hit1.png");
    	loadImage("Wukong_Hit2Image", "wukong_hit2.png");
    	loadImage("Wukong_Attack1Image", "wukong_attack1.png");
    	loadImage("Wukong_Attack2Image", "wukong_attack2.png");
    	loadImage("Wukong_Attack3Image", "wukong_attack3.png");
    	loadImage("Wukong_Attack4Image", "wukong_attack4.png");
    	
        loadAudio("ButtonSound", "buttonSound.wav");
        loadAudio("FailureSound", "failureSound.wav");
        loadAudio("GameSound", "gamesound.wav");
        loadAudio("HomePageSound", "homepageSound.wav");
        loadAudio("NextWaveSound", "nextwaveSound.wav");
        
    }
    
    private static void loadImage(String key, String fileName) {
    	IMAGES.put(key, new Image(ClassLoader.getSystemResource(fileName).toExternalForm()));
    }

    private static void loadAudio(String key, String fileName) {
    	AUDIO_CLIPS.put(key, new AudioClip(ClassLoader.getSystemResource(fileName).toExternalForm()));
    }

    public static Image getImage(String key) {
        return IMAGES.get(key);
    }

    public static AudioClip getAudio(String key) {
        return AUDIO_CLIPS.get(key);
    }
}

 