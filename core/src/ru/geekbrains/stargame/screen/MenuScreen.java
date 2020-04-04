package ru.geekbrains.stargame.screen;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Vector2;
import ru.geekbrains.stargame.base.BaseScreen;
import ru.geekbrains.stargame.exceptions.GameException;
import ru.geekbrains.stargame.math.Rect;
import ru.geekbrains.stargame.sprites.BackgroundSprite;
import ru.geekbrains.stargame.sprites.ExitButton;
import ru.geekbrains.stargame.sprites.PlayButton;

public class MenuScreen extends BaseScreen {
  private final Game game;

  private BackgroundSprite backgroundSprite;
  private TextureAtlas menuAtlas;
  private TextureAtlas gameAtlas;
  private ExitButton exitButton;
  private PlayButton playButton;

  public MenuScreen(final Game game) {
    this.game = game;
  }

  @Override
  public void show() {
    super.show();

    menuAtlas = new TextureAtlas(Gdx.files.internal("textures/menuAtlas.pack"));
    gameAtlas = new TextureAtlas(Gdx.files.internal("textures/StarGame.atlas"));

    try {
      backgroundSprite = new BackgroundSprite(gameAtlas);
      exitButton = new ExitButton(menuAtlas);
      playButton = new PlayButton(menuAtlas, game);
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
    exitButton.resize(worldBounds);
    playButton.resize(worldBounds);
  }

  @Override
  public void dispose() {
    menuAtlas.dispose();
    gameAtlas.dispose();
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
    exitButton.touchUp(touch, pointer, button);
    playButton.touchUp(touch, pointer, button);
    return false;
  }

  @Override
  public boolean touchDown(final Vector2 touch, final int pointer, final int button) {
    exitButton.touchDown(touch, pointer, button);
    playButton.touchDown(touch, pointer, button);
    return false;
  }

  private void draw() {
    Gdx.gl.glClearColor(0, 0, 0, 1);
    Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
    batch.begin();
    backgroundSprite.draw(batch);
    playButton.draw(batch);
    exitButton.draw(batch);
    batch.end();
  }
}
