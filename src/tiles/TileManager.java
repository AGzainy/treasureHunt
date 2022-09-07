package tiles;

import java.awt.Graphics2D;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import javax.imageio.ImageIO;

import main.GamePanel;
import main.UtilityTool;

public class TileManager {

	GamePanel gp;
	public Tile[] tile;
	public int mapTileNum[][];
	
	public TileManager(GamePanel gp) {
		this.gp = gp;
		tile = new Tile[50];
		mapTileNum = new int[gp.maxWorldCol][gp.maxWorldRow];
		getTileImage();
		loadMap("/maps/world01.txt");
		
	}
	
	public void getTileImage() {

			setup(0, "grassO",false);
			
			setup(1, "wall", true);
		
			setup(2,"water",true);
			
			setup(3,"earth",false);
			
			setup(4,"tree0",true);
			
			setup(5,"sand",false);
			
			setup(6,"grassU",false);
			
			setup(7,"flowers",false);
			
			setup(8,"topWater",true);
			
			setup(9,"leftsideWater",true);
			
			setup(10,"rightsideWater",true);
			
			setup(11,"downsideWater",true);
			
			setup(12,"leftTopCorner",true);
			
      		setup(13,"rightBottomCorner",true);
			
			setup(14,"water0",true);
			
			setup(15,"sandH",false);

			setup(16,"leftBottomCorner",true);
			
			setup(17,"sandM",false);
			
			setup(18,"toprRightCorner",true);
			
			setup(19,"topLeft",true);
			
			setup(20,"bottomLeft",true);
			
			setup(21,"bottomRight",true);
			
			setup(22,"topRight",true);

			setup(23,"sandB",false);

			setup(24,"sandT",false);

			setup(25,"sandR",false);
			
			setup(26,"sandBR",false);
			
			setup(27,"sandTR",false);
			
			setup(28,"sandTL",false);
			
			setup(29,"sandL",false);
			
			setup(30,"sandBL",false);


}
	public void setup(int index, String imageName, boolean collision) {
		
		UtilityTool uTool = new UtilityTool();
		
		try {
			tile[index] = new Tile();
			tile[index].image = ImageIO.read(getClass().getResourceAsStream("/tiles/"+imageName +".png"));
			tile[index].image = uTool.scaleImage(tile[index].image, gp.tileSize, gp.tileSize);
			tile[index].collision = collision;
			
		}catch(IOException e) {
			e.printStackTrace();
		}
		
	}
	
	public void loadMap(String mapPath) {
		
		try {
			
			InputStream is = getClass().getResourceAsStream(mapPath);
			BufferedReader br = new BufferedReader(new InputStreamReader(is));
			
			int col = 0;
			int row = 0;
			
			while(col < gp.maxWorldCol && row < gp.maxWorldRow) {
				
				String line = br.readLine();//reads a line of text
				
				while(col < gp.maxWorldCol) {
					
					String numbers[] = line.split(" ");
					int num = Integer.parseInt(numbers[col]);
					
					mapTileNum[col][row] = num;
					col++;	
				}
				if(col == gp.maxWorldCol) {
					col = 0;
					row ++;
				}
			}
			br.close();
			
		}catch(Exception e) {
			
		}
	}
	public void draw(Graphics2D g2) {
		
		int worldCol = 0;
		int worldRow = 0;
		
		
		while(worldCol < gp.maxWorldCol && worldRow < gp.maxWorldRow) {
			
			int tileNum = mapTileNum[worldCol][worldRow];
			
			int worldX = worldCol * gp.tileSize;
			int worldY = worldRow * gp.tileSize;
			int screenX = worldX - gp.player.worldX + gp.player.screenX;
			int screenY = worldY - gp.player.worldY + gp.player.screenY;
			
			if( worldX + gp.tileSize > gp.player.worldX - gp.player.screenX &&
			    worldX - gp.tileSize < gp.player.worldX + gp.player.screenX &&
				worldY + gp.tileSize > gp.player.worldY - gp.player.screenY && 
				worldY - gp.tileSize < gp.player.worldY + gp.player.screenY) {
			
			g2.drawImage(tile[tileNum].image, screenX, screenY,  null);
			} 
			worldCol++;
		   
			
		    if(worldCol == gp.maxWorldCol) {
		    	worldCol =0;
		    	
		    	worldRow ++;
		    	
		    }
			
		}
		
		
		
		
		//test
//		for(int y=0; y<193;y+=48) {
//		for(int x=0;x<193;x+=48) {
//			if(y<48 || x <48 || (x>144 && y != 96)) {
// 	g2.drawImage(tile[1].image, x, y, gp.tileSize, gp.tileSize,null);
//			}else if(x >47 && x < 192 && y >144) {
//				g2.drawImage(tile[2].image, x, y, gp.tileSize, gp.tileSize,null);
//
//			}else{
//				g2.drawImage(tile[0].image, x, y, gp.tileSize, gp.tileSize,null);
//	
//			}
//			
//		}
//		}
		
		
	}
}
