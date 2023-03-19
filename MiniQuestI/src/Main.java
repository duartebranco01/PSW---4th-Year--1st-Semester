import java.util.Scanner;

public class Main {

    public static int death(Hero myHero, Dragon myDragon)
    {
        if((myHero.posX==myDragon.posX+1 && myHero.posY==myDragon.posY) ||
                (myHero.posX==myDragon.posX-1 && myHero.posY==myDragon.posY) ||
                (myHero.posY==myDragon.posY+1 && myHero.posX==myDragon.posX) ||
                (myHero.posY==myDragon.posY-1 && myHero.posX==myDragon.posX) ||
                (myHero.posX==myDragon.posX && myHero.posY==myDragon.posY)) return 1;
        else return 0;
    }

    public static int moveHero(Hero myHero, String[][] arrayDisplay)
    {

        if("X ".equals(arrayDisplay[myHero.posY][myHero.posX]) ||
                "X".equals(arrayDisplay[myHero.posY][myHero.posX]) ||
                ("E ".equals(arrayDisplay[myHero.posY][myHero.posX]) && myHero.hasKey!=1) ) //y,x
        {
            System.out.println("Collision");
            return  1;
        } else if ("E ".equals(arrayDisplay[myHero.posY][myHero.posX]) && myHero.hasKey==1)
        {
            arrayDisplay[myHero.oldPosY][myHero.oldPosX]="  ";
            arrayDisplay[myHero.posY][myHero.posX]="H ";
            return 2;
        }
        else
        {
            arrayDisplay[myHero.oldPosY][myHero.oldPosX]="  ";
            arrayDisplay[myHero.posY][myHero.posX]="H ";
            return 0;
        }
    }
    public static int printDisplay(Hero myHero, Dragon myDragon, int keyPosX, int keyPosY, Exit myExit, String[][] arrayDisplay)
    {
        int flag=0;

        System.out.println("heroX= " + myHero.posX);
        System.out.println("heroY= " + myHero.posY);
        System.out.println("oldheroX= " + myHero.oldPosX);
        System.out.println("oldheroY= " + myHero.oldPosY);


        if(myHero.posX<0 || myDragon.posX<0 || keyPosX<0 ||
                myHero.posX>arrayDisplay[0].length || myDragon.posX>arrayDisplay[0].length || keyPosX>arrayDisplay[0].length ||
                myHero.posY<0 || myDragon.posY<0 || keyPosY<0 ||
                myHero.posY>arrayDisplay.length || myDragon.posY>arrayDisplay.length || keyPosY>arrayDisplay.length)
        {
            System.out.println("Error: Invalid position");
            return -1;
        }

        if((myHero.posX!=keyPosX || myHero.posY!=keyPosY) && myHero.hasKey==0) arrayDisplay[keyPosY][keyPosX]="K ";
        else
        {
            arrayDisplay[keyPosY][keyPosX]="  ";
            myHero.hasKey=1;
        }

        //se hasKey mudar Exit e Dragon....

        arrayDisplay[myExit.posY][myExit.posX]="E ";

        flag=moveHero(myHero, arrayDisplay);
        //asdsfdf
        if(death(myHero, myDragon)==1) flag=3;

        arrayDisplay[myDragon.posY][myDragon.posX]= "D ";


        for(int i=0; i< arrayDisplay.length; i++)
        {
            for(int j=0; j< arrayDisplay[0].length; j++)
            {
                System.out.print(arrayDisplay[i][j]);
            }
            System.out.println();
        }

        System.out.println("hasKey= " + myHero.hasKey);
        System.out.println("flag= " + flag);


        return  flag;
    }


    public static void main(String[] args) {

        String[][] arrayDisplay = {
                {"X ", "X ", "X ", "X ", "X ", "X ", "X ", "X ", "X ", "X"},
                {"X ", "  ", "  ", "  ", "  ", "  ", "  ", "  ", "  ", "X"},
                {"X ", "  ", "X ", "X ", "  ", "X ", "  ", "X ", "  ", "X"},
                {"X ", "  ", "X ", "X ", "  ", "X ", "  ", "X ", "  ", "X"},
                {"X ", "  ", "X ", "X ", "  ", "X ", "  ", "X ", "  ", "X"},
                {"X ", "  ", "  ", "  ", "  ", "  ", "  ", "X ", "  ", "X"},
                {"X ", "  ", "X ", "X ", "  ", "X ", "  ", "X ", "  ", "X"},
                {"X ", "  ", "X ", "X ", "  ", "X ", "  ", "X ", "  ", "X"},
                {"X ", "  ", "X ", "X ", "  ", "  ", "  ", "  ", "  ", "X"},
                {"X ", "X ", "X ", "X ", "X ", "X ", "X ", "X ", "X ", "X"}
        };

        Hero myHero = new Hero();
        Dragon myDragon = new Dragon();
        Exit myExit = new Exit();

        myHero.posX=1;
        myHero.posY=1;
        myHero.oldPosX=1;
        myHero.oldPosY=1;
        myHero.hasKey=0;

        myDragon.posX=1;
        myDragon.posY=3;
        myDragon.oldPosX=1;
        myDragon.oldPosY=3;

        myExit.posX=9;
        myExit.posY=5;

        int keyPosX = 1, keyPosY = 8, flag=0;
        Scanner input = new Scanner(System.in);

        System.out.println("rows= " + arrayDisplay.length);
        System.out.println("cols= " + arrayDisplay[0].length);

        while (true)
        {
            flag=printDisplay(myHero, myDragon, keyPosX, keyPosY, myExit, arrayDisplay);
            if(flag==1)
            {
                myHero.posX = myHero.oldPosX;
                myHero.posY = myHero.oldPosY;
            } else if (flag==0)
            {
                myHero.oldPosX = myHero.posX;
                myHero.oldPosY = myHero.posY;
            }
            else if (flag==2)
            {
                System.out.println("YOU WIN!");
                return;
            }
            else if (flag==3)
            {
                System.out.println("YOU DIE!");
                return;
            }
            else if (flag==-1) return;

            //System.out.println("hasKey=" + myHero.hasKey);

            while (true)
            {
                char move=input.next().charAt(0);
                if(move=='w'){ myHero.posY--; break;}
                else if(move=='s'){ myHero.posY++; break;}
                else if(move=='a'){ myHero.posX--; break;}
                else if(move=='d'){ myHero.posX++; break;}
            }
        }
    }
}