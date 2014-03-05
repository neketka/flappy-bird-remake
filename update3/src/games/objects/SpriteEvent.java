package games.objects;

/**
 * Created by Nikita on 3/4/14.
 */
public class SpriteEvent {
    private Sprite[] collider;
    private Sprite source;
    public SpriteEvent(Sprite[] collider,Sprite sou){
        this.collider = collider;
        source = sou;
    }
    public Sprite getSource() {
        return source;
    }
    public Sprite[] getCollider() {
        return collider;
    }
}
