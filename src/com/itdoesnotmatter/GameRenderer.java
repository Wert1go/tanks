package com.itdoesnotmatter;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import android.content.Context;
import android.opengl.GLSurfaceView;
import android.util.Log;

public class GameRenderer implements GLSurfaceView.Renderer{
	private static int TICK_PER_SECOND = 25;
	private static int SKIP_TICKS = 1000/TICK_PER_SECOND;
	private static int MAX_FRAME_SKIP = 5;
	private int FPS = 0;
	private int FPS_COUNTER = 0;
	Context mContext;
	Tile mPet;
	Context context;
	int mWidth;
	int mHeight;
	GameMap mMap;
	Tank tank;
	CollisionDetection mCollisionManager;
	public GUI mInterface;
	float angle = 0;
	boolean flag = true;
	long next_game_tick;
	private long elapsedTime = 0;
	private long startTime = 0;
	private long second = 0;
	private long startFPSMeasure = 0;
	boolean move = false;
	boolean random = false;
	Textures textures;
	
	private World world;
	
	public GameRenderer(Context context, boolean random) {
		this.context = context;
		this.random = random;
	}
	
	public void updateObjects() {
		world.tick();
	}
	
	private void updateGraphic(GL10 gl, float interpolation) {
		//Очистка буферов цвета и глубины
		gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
		//Очистка матрицы
		gl.glLoadIdentity();		
		//Здесь начали рисовать
		//Перенес включение и выключение массивов вершин и др настроет сюда из GameObject FPS вырос на 1-2
		//Перенес gl.glEnableClientState(GL10.GL_TEXTURE_COORD_ARRAY); еще + 1 FPS
		//Указываем какое то направление
		gl.glFrontFace(GL10.GL_CCW);
		//Обрезать заднюю часть изображения
		gl.glEnable(GL10.GL_CULL_FACE);
		gl.glCullFace(GL10.GL_BACK);
		//Включение массовов для работы с вершинами и координатам
		gl.glEnableClientState(GL10.GL_VERTEX_ARRAY);
		gl.glEnableClientState(GL10.GL_TEXTURE_COORD_ARRAY);
		
		
		//drawQueue(gl, interpolation);
		world.render(gl, interpolation);
		
		//Матрица пуш!
		gl.glPushMatrix();
				//Отрисовка карты
				
				//mMap.draw(gl);
		mInterface.draw(gl);
					//Матрица назад!
		gl.glPopMatrix();		
		//Отключаем включенные режимы
		gl.glDisableClientState(GL10.GL_TEXTURE_COORD_ARRAY);
		gl.glDisableClientState(GL10.GL_VERTEX_ARRAY);
		gl.glDisable(GL10.GL_CULL_FACE);
		//Здесь закончили
		gl.glFlush();
		FPS_COUNTER++;
	}
	
	private void gameLoop(GL10 gl) {
		/*
		 * Взято с хабра
		 * Обновление состояния игры происходит с частотой TICK_PER_SECOND
		 * Обновление графического представления так часто, на сколько это возможно
		 * Замечена проблема: При низком FPS представление, чтобы угнаться за моделью рисуется рывками, т.к.
		 * данные меняются слишком быстро интерполяция не особо помогает
		 * 
		 */
		
		int loops = 0;
		float interpolation;
		
		 while(this.getTime() > next_game_tick && loops < MAX_FRAME_SKIP) {
			
			updateObjects();

			next_game_tick += SKIP_TICKS;
			loops++;
		 }

		 interpolation = (float) (this.getTime() + SKIP_TICKS - next_game_tick)
	                        / (float) SKIP_TICKS ;
		
		 updateGraphic(gl, interpolation);

		 fpsCounter();
	}
	
	private long getTime() {
		/*
		 * Определение времени прошедшего с момента запуска игры
		 */
		this.elapsedTime = System.currentTimeMillis() - this.startTime;
		
		return this.elapsedTime;
	}
	
	private void fpsCounter() {
		/*
		 * Подсчет FPS и вывод его на консоль
		 * TODO: Графическое представление
		 */
		second = System.currentTimeMillis() - this.startFPSMeasure;
		if (second >= 1000) {
			second = 0;
			this.startFPSMeasure = System.currentTimeMillis();
			FPS = FPS_COUNTER;
			FPS_COUNTER = 0;
			Log.d("FPS", " : " + FPS);
		}
		
		//drawFPS
	}
	
	/**************** OPEN GL STAFF ***************/
	
	@Override
	public void onDrawFrame(GL10 gl) {
		/*
		 * Не уверен, что здесь место игровому циклу
		 */
		gameLoop(gl);
	}

	@Override
	public void onSurfaceChanged(GL10 gl, int width, int height) {
		/*
		 * Идет сразу за onSurfaceCreated
		 * Здесь фиксируем размеры экрана и от них пляшем
		 */
		
		
		
		mWidth = width;
		mHeight = height;
		textures = new Textures(context, gl);
		world = new World(width, height);

		tank = new Tank();
		int y = 12;
		int x = 13;
		
		Tank tank1 = new Tank();
		Tank tank2 = new Tank();
		Tank tank3 = new Tank();
		Tank tank4 = new Tank();
		
		world.placeObject(tank1, 12, 15);
		world.placeObject(tank2, 15, 15);
		world.placeObject(tank3, 16, 15);	
		world.placeObject(tank4, 8, 15);
		world.placeObject(tank, y, x);
		
		world.addObject(tank);
		world.addObject(tank1);
		world.addObject(tank2);
		world.addObject(tank3);
		world.addObject(tank4);
		
		//mCollisionManager = new CollisionDetection(mMap);
		mInterface = new GUI();

		// Установка порта просмотра на текущий экран
		gl.glViewport(0, 0, width, height);
		gl.glMatrixMode(GL10.GL_PROJECTION);
		// Очистка матрицы
		gl.glLoadIdentity();
		//Установка системы координат с началом (0, 0) в левом нижнум углу (в левом верхнем пока почуму-то не получается)
		gl.glOrthof(0.0f, width, 0f, height, -1f, 1f);		
		gl.glMatrixMode(GL10.GL_MODELVIEW);
		gl.glLoadIdentity();
	}

	@Override
	public void onSurfaceCreated(GL10 gl, EGLConfig config) {
		/*
		 * Установка необходимых настроек и фиксирование времни старта
		 */
        gl.glHint(GL10.GL_PERSPECTIVE_CORRECTION_HINT, GL10.GL_FASTEST);

        gl.glClearColor(0.0f, 0.0f, 0.0f, 1);
        gl.glShadeModel(GL10.GL_FLAT);
        gl.glDisable(GL10.GL_DEPTH_TEST);
        gl.glEnable(GL10.GL_TEXTURE_2D);
        gl.glEnable(GL10.GL_BLEND);
	    gl.glBlendFunc(GL10.GL_ONE, GL10.GL_ONE_MINUS_SRC_ALPHA);
        /*
         * By default, OpenGL enables features that improve quality but reduce
         * performance. One might want to tweak that especially on software
         * renderer.
         */
        gl.glDisable(GL10.GL_DITHER);
        gl.glDisable(GL10.GL_LIGHTING);

        gl.glTexEnvx(GL10.GL_TEXTURE_ENV, GL10.GL_TEXTURE_ENV_MODE, GL10.GL_MODULATE);

        gl.glClear(GL10.GL_COLOR_BUFFER_BIT | GL10.GL_DEPTH_BUFFER_BIT);
        
		this.startTime = System.currentTimeMillis();
		next_game_tick = this.getTime();
	}

}
