package assets;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.media.AudioClip;

public class ResourceLoader {
    
    private static Image HomePageImage;
    private static Image backgroundImage;
    
    private static Image baseplantImage;
    private static Image superplantImage;
    private static Image trapplantImage;
    
    private static Image bulletImage;
    
    private static Image kappa_walk1Image;
    private static Image kappa_walk2Image;
    private static Image kappa_deadImage;
    private static Image kappa_hit1Image;
    private static Image kappa_hit2Image;
    private static Image kappa_attack1Image;
    private static Image kappa_attack2Image;
    private static Image kappa_attack3Image;
    private static Image kappa_attack4Image;
    
    private static Image berserker_walk1Image;
    private static Image berserker_walk2Image;
    private static Image berserker_deadImage;
    private static Image berserker_hit1Image;
    private static Image berserker_hit2Image;
    private static Image berserker_attack1Image;
    private static Image berserker_attack2Image;
    private static Image berserker_attack3Image;
    private static Image berserker_attack4Image;
    
    private static Image wukong_walk1Image;
    private static Image wukong_walk2Image;
    private static Image wukong_deadImage;
    private static Image wukong_hit1Image;
    private static Image wukong_hit2Image;
    private static Image wukong_attack1Image;
    private static Image wukong_attack2Image;
    private static Image wukong_attack3Image;
    private static Image wukong_attack4Image;
    
    public static AudioClip buttonSound;
    public static AudioClip failureSound;
    public static AudioClip gameSound;
    public static AudioClip homepageSound;
    public static AudioClip nextwaveSound;

    // This method loads all resources at once
    public static void loadResources() {
        // Load resources
        HomePageImage = new Image(ClassLoader.getSystemResource("Homepage.png").toExternalForm());
        backgroundImage = new Image(ClassLoader.getSystemResource("background.png").toExternalForm());
        
        baseplantImage = new Image(ClassLoader.getSystemResource("baseplant.png").toExternalForm());
        superplantImage = new Image(ClassLoader.getSystemResource("superplant.png").toExternalForm());
        trapplantImage = new Image(ClassLoader.getSystemResource("trapplant.png").toExternalForm());
        
        bulletImage = new Image(ClassLoader.getSystemResource("bullet.png").toExternalForm());
        
        kappa_walk1Image = new Image(ClassLoader.getSystemResource("kappa_walk1.png").toExternalForm());
        kappa_walk2Image = new Image(ClassLoader.getSystemResource("kappa_walk2.png").toExternalForm());
        kappa_deadImage = new Image(ClassLoader.getSystemResource("kappa_dead.png").toExternalForm());
        kappa_hit1Image = new Image(ClassLoader.getSystemResource("kappa_hit1.png").toExternalForm());
        kappa_hit2Image = new Image(ClassLoader.getSystemResource("kappa_hit2.png").toExternalForm());
        kappa_attack1Image = new Image(ClassLoader.getSystemResource("kappa_attack1.png").toExternalForm());
        kappa_attack2Image = new Image(ClassLoader.getSystemResource("kappa_attack2.png").toExternalForm());
        kappa_attack3Image = new Image(ClassLoader.getSystemResource("kappa_attack3.png").toExternalForm());
        kappa_attack4Image = new Image(ClassLoader.getSystemResource("kappa_attack4.png").toExternalForm());
        
        berserker_walk1Image = new Image(ClassLoader.getSystemResource("berserker_walk1.png").toExternalForm());
        berserker_walk2Image = new Image(ClassLoader.getSystemResource("berserker_walk2.png").toExternalForm());
        berserker_deadImage = new Image(ClassLoader.getSystemResource("berserker_dead.png").toExternalForm());
        berserker_hit1Image = new Image(ClassLoader.getSystemResource("berserker_hit1.png").toExternalForm());
        berserker_hit2Image = new Image(ClassLoader.getSystemResource("berserker_hit2.png").toExternalForm());
        berserker_attack1Image = new Image(ClassLoader.getSystemResource("berserker_attack1.png").toExternalForm());
        berserker_attack2Image = new Image(ClassLoader.getSystemResource("berserker_attack2.png").toExternalForm());
        berserker_attack3Image = new Image(ClassLoader.getSystemResource("berserker_attack3.png").toExternalForm());
        berserker_attack4Image = new Image(ClassLoader.getSystemResource("berserker_attack4.png").toExternalForm());
        
        wukong_walk1Image = new Image(ClassLoader.getSystemResource("wukong_walk1.png").toExternalForm());
        wukong_walk2Image = new Image(ClassLoader.getSystemResource("wukong_walk2.png").toExternalForm());
        wukong_deadImage = new Image(ClassLoader.getSystemResource("wukong_dead.png").toExternalForm());
        wukong_hit1Image = new Image(ClassLoader.getSystemResource("wukong_hit1.png").toExternalForm());
        wukong_hit2Image = new Image(ClassLoader.getSystemResource("wukong_hit2.png").toExternalForm());
        wukong_attack1Image = new Image(ClassLoader.getSystemResource("wukong_attack1.png").toExternalForm());
        wukong_attack2Image = new Image(ClassLoader.getSystemResource("wukong_attack2.png").toExternalForm());
        wukong_attack3Image = new Image(ClassLoader.getSystemResource("wukong_attack3.png").toExternalForm());
        wukong_attack4Image = new Image(ClassLoader.getSystemResource("wukong_attack4.png").toExternalForm());
        
        buttonSound = new AudioClip(ClassLoader.getSystemResource("buttonSound.wav").toExternalForm());
        failureSound = new AudioClip(ClassLoader.getSystemResource("failureSound.wav").toExternalForm());
        gameSound = new AudioClip(ClassLoader.getSystemResource("gamesound.wav").toExternalForm());
        homepageSound = new AudioClip(ClassLoader.getSystemResource("homepageSound.wav").toExternalForm());
        nextwaveSound = new AudioClip(ClassLoader.getSystemResource("nextwaveSound.wav").toExternalForm());
    }

 // Getter methods to access resources
    
    public static Image getHomePageImage() {
		return HomePageImage;
	}

	public static Image getBackgroundImage() {
		return backgroundImage;
	}

	public static Image getBaseplantImage() {
		return baseplantImage;
	}

	public static Image getSuperplantImage() {
		return superplantImage;
	}

	public static Image getTrapplantImage() {
		return trapplantImage;
	}

	public static Image getBulletImage() {
		return bulletImage;
	}

	public static Image getKappa_walk1Image() {
		return kappa_walk1Image;
	}

	public static Image getKappa_walk2Image() {
		return kappa_walk2Image;
	}

	public static Image getKappa_deadImage() {
		return kappa_deadImage;
	}

	public static Image getKappa_hit1Image() {
		return kappa_hit1Image;
	}

	public static Image getKappa_hit2Image() {
		return kappa_hit2Image;
	}

	public static Image getKappa_attack1Image() {
		return kappa_attack1Image;
	}

	public static Image getKappa_attack2Image() {
		return kappa_attack2Image;
	}

	public static Image getKappa_attack3Image() {
		return kappa_attack3Image;
	}

	public static Image getKappa_attack4Image() {
		return kappa_attack4Image;
	}

	public static Image getBerserker_walk1Image() {
		return berserker_walk1Image;
	}

	public static Image getBerserker_walk2Image() {
		return berserker_walk2Image;
	}

	public static Image getBerserker_deadImage() {
		return berserker_deadImage;
	}

	public static Image getBerserker_hit1Image() {
		return berserker_hit1Image;
	}

	public static Image getBerserker_hit2Image() {
		return berserker_hit2Image;
	}

	public static Image getBerserker_attack1Image() {
		return berserker_attack1Image;
	}

	public static Image getBerserker_attack2Image() {
		return berserker_attack2Image;
	}

	public static Image getBerserker_attack3Image() {
		return berserker_attack3Image;
	}

	public static Image getBerserker_attack4Image() {
		return berserker_attack4Image;
	}

	public static Image getWukong_walk1Image() {
		return wukong_walk1Image;
	}

	public static Image getWukong_walk2Image() {
		return wukong_walk2Image;
	}

	public static Image getWukong_deadImage() {
		return wukong_deadImage;
	}

	public static Image getWukong_hit1Image() {
		return wukong_hit1Image;
	}

	public static Image getWukong_hit2Image() {
		return wukong_hit2Image;
	}

	public static Image getWukong_attack1Image() {
		return wukong_attack1Image;
	}

	public static Image getWukong_attack2Image() {
		return wukong_attack2Image;
	}

	public static Image getWukong_attack3Image() {
		return wukong_attack3Image;
	}

	public static Image getWukong_attack4Image() {
		return wukong_attack4Image;
	}

	public static AudioClip getButtonSound() {
		return buttonSound;
	}

	public static AudioClip getFailureSound() {
		return failureSound;
	}

	public static AudioClip getGameSound() {
		return gameSound;
	}

	public static AudioClip getHomepageSound() {
		return homepageSound;
	}

	public static AudioClip getNextwaveSound() {
		return nextwaveSound;
	}
 
}
