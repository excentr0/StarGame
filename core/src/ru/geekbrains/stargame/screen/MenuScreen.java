package ru.geekbrains.stargame.screen;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import ru.geekbrains.stargame.base.BaseScreen;
import ru.geekbrains.stargame.exceptions.GameException;
import ru.geekbrains.stargame.math.Rect;
import ru.geekbrains.stargame.sprites.BackgroundSprite;
import ru.geekbrains.stargame.sprites.ButtonExit;
import ru.geekbrains.stargame.sprites.ButtonPlay;

public class MenuScreen extends BaseScreen {
  private final Game game;
  private BackgroundSprite backgroundSprite;
  private TextureAtlas menuAtlas;
  private ButtonExit buttonExit;
  private ButtonPlay buttonPlay;

  public MenuScreen(final Game game) {
    this.game = game;
  }

  @Override
  public void show() {
    super.show();

    menuAtlas = new TextureAtlas(Gdx.files.internal("textures/menuAtlas.pack"));

    final TextureAtlas gameAtlas = new TextureAtlas("StarGame.pack");
    final TextureRegion background = gameAtlas.findRegion("background");

    try {
      backgroundSprite = new BackgroundSprite(background);
      buttonExit = new ButtonExit(menuAtlas);
      buttonPlay = new ButtonPlay(menuAtlas, game);
    } catch (GameException e) {
      throw new RuntimeException(e);
    }
  }

  @Override
  public void render(final float delta) {
    draw();
  }

  @Override
  public void resize(final Rect worldBounds) {
    backgroundSprite.resize(worldBounds);
    buttonExit.resize(worldBounds);
    buttonPlay.resize(worldBounds);
  }

  @Override
  public void dispose() {
    menuAtlas.dispose();
    super.dispose();
  }

  @Override
  public boolean keyDown(final int keycode) {
    return super.keyDown(keycode);
  }

  @Override
  public boolean keyUp(final int keycode) {
    return super.keyUp(keycode);
  }

  @Override
  public boolean touchUp(final Vector2 touch, final int pointer, final int button) {
    buttonExit.touchUp(touch, pointer, button);
    buttonPlay.touchUp(touch, pointer, button);
    return false;
  }

  @Override
  public boolean touchDown(final Vector2 touch, final int pointer, final int button) {
    buttonExit.touchDown(touch, pointer, button);
    buttonPlay.touchDown(touch, pointer, button);
    return false;
  }

  private void draw() {
    Gdx.gl.glClearColor(0, 0, 0, 1);
    Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
    batch.begin();
    backgroundSprite.draw(batch);
    buttonPlay.draw(batch);
    buttonExit.draw(batch);
    batch.end();
  }
}
