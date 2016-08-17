package com.mygdx.game.handlers;

import static com.mygdx.game.handlers.Box2DVariables.PPM;

import java.util.ArrayList;

import com.badlogic.gdx.physics.box2d.World;
import com.mygdx.game.api.Box2DTerrain;
import com.mygdx.game.api.Box2DVehicle;
import com.mygdx.game.entities.other.BoxLoad;
import com.mygdx.game.stages.Stage02;
import com.mygdx.game.stages.Stage03;
import com.mygdx.game.stages.Stage04;
import com.mygdx.game.stages.Stage05;
import com.mygdx.game.stages.Terrain0;
import com.mygdx.game.vehicles.FarmTruck;

public class StageManager {
	public static final int STAGE_1 = 0;
	public static final int STAGE_2 = 1;
	public static final int STAGE_3 = 2;
	public static final int STAGE_4 = 3;
	public static final int STAGE_5 = 4;

	public static Box2DVehicle getVehicle(World world, int stage) {
		switch (stage) {
			case STAGE_1:
				return new FarmTruck(world, 150 / PPM, 130 / PPM, 2);
		
			case STAGE_2:
				return new FarmTruck(world, 150 / PPM, 130 / PPM, 2);
			
			case STAGE_3:
				return new FarmTruck(world, 150 / PPM, 130 / PPM, 2);
			
			case STAGE_4:
				return new FarmTruck(world, 150 / PPM, 200 / PPM, 2);
			
			case STAGE_5:
				return new FarmTruck(world, 150 / PPM, 200 / PPM, 2);
		}

		return null;
	}
	
	public static ArrayList<BoxLoad> getLoad(World world, int stage) {
		ArrayList<BoxLoad> truckLoad = new ArrayList<BoxLoad>();
		switch(stage){
			case STAGE_1:
				truckLoad.add( new BoxLoad(world, 40, 40, 90 / PPM, 300 / PPM) );
				truckLoad.add( new BoxLoad(world, 40, 40, 130 / PPM, 300 / PPM) );
				
				truckLoad.add( new BoxLoad(world, 40, 40, 90 / PPM, 350 / PPM) );
				truckLoad.add( new BoxLoad(world, 40, 40, 130 / PPM, 350 / PPM) );
				
				//truckLoad.add( new BoxLoad(world, 40, 40, 90 / PPM, 400 / PPM) );
				//truckLoad.add( new BoxLoad(world, 40, 40, 130 / PPM, 400 / PPM) );
				return truckLoad;
		
			case STAGE_2:
				truckLoad.add( new BoxLoad(world, 40, 40, 90 / PPM, 300 / PPM) );
				truckLoad.add( new BoxLoad(world, 40, 40, 130 / PPM, 300 / PPM) );
				
				truckLoad.add( new BoxLoad(world, 40, 40, 90 / PPM, 350 / PPM) );
				truckLoad.add( new BoxLoad(world, 40, 40, 130 / PPM, 350 / PPM) );
				
				//truckLoad.add( new BoxLoad(world, 40, 40, 90 / PPM, 400 / PPM) );
				//truckLoad.add( new BoxLoad(world, 40, 40, 130 / PPM, 400 / PPM) );
				return truckLoad;
			
			case STAGE_3:
				truckLoad.add( new BoxLoad(world, 40, 40, 90 / PPM, 300 / PPM) );
				truckLoad.add( new BoxLoad(world, 40, 40, 130 / PPM, 300 / PPM) );
				
				truckLoad.add( new BoxLoad(world, 40, 40, 90 / PPM, 350 / PPM) );
				truckLoad.add( new BoxLoad(world, 40, 40, 130 / PPM, 350 / PPM) );
				
				//truckLoad.add( new BoxLoad(world, 40, 40, 90 / PPM, 400 / PPM) );
				//truckLoad.add( new BoxLoad(world, 40, 40, 130 / PPM, 400 / PPM) );
				return truckLoad;
			
			case STAGE_4:
				truckLoad.add( new BoxLoad(world, 40, 40, 85 / PPM, 200 / PPM) );
				truckLoad.add( new BoxLoad(world, 40, 40, 125 / PPM, 200 / PPM) );
				
				truckLoad.add( new BoxLoad(world, 40, 40, 85 / PPM, 250 / PPM) );
				truckLoad.add( new BoxLoad(world, 40, 40, 125 / PPM, 250 / PPM) );
				
				//truckLoad.add( new BoxLoad(world, 40, 40, 90 / PPM, 400 / PPM) );
				//truckLoad.add( new BoxLoad(world, 40, 40, 130 / PPM, 400 / PPM) );
				return truckLoad;
		
			case STAGE_5:
				truckLoad.add( new BoxLoad(world, 40, 40, 85 / PPM, 200 / PPM) );
				truckLoad.add( new BoxLoad(world, 40, 40, 125 / PPM, 200 / PPM) );
				
				truckLoad.add( new BoxLoad(world, 40, 40, 85 / PPM, 250 / PPM) );
				truckLoad.add( new BoxLoad(world, 40, 40, 125 / PPM, 250 / PPM) );
				
				//truckLoad.add( new BoxLoad(world, 40, 40, 90 / PPM, 400 / PPM) );
				//truckLoad.add( new BoxLoad(world, 40, 40, 130 / PPM, 400 / PPM) );
				return truckLoad;
		}
		
		return null;
	}
	
	public static Box2DTerrain getTerrain(World world, int stage) {
		switch (stage) {
			case STAGE_1:
				return new Terrain0(world, 0 / PPM, 0 / PPM, 10);
			
			case STAGE_2:
				return new Stage02(world, 0 / PPM, 0 / PPM, 10, 10);
			
			case STAGE_3:
				return new Stage03(world, 0 / PPM, 0 / PPM, 10, 10);
				
			case STAGE_4:
				return new Stage04(world, 0 / PPM, 0 / PPM, 10, 10);
				
			case STAGE_5:
				return new Stage05(world, 0 / PPM, 0 / PPM, 10, 10);
		}

		return null;
	}
	
	public static int getStageNum(int i){
		switch(i){
			case 0:
				return STAGE_1;
			case 1:
				return STAGE_2;
			case 2:
				return STAGE_3;
			case 3:
				return STAGE_4;
			case 4:
				return STAGE_5;
		}
	
		return -1;
	}
}
