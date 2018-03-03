package com.scorer.ModalClass;

/**
 * Created by impowersolutionspvtltd on 31/05/17.
 */

public class GameModel {

    public static boolean Game_single_status;
    public static int Game_count;
    public static int Game_player_count = 1;
    public static String Game_id = "";
    public static String Game_name = "";
    public static String Game_player_id_1 = "";
    public static String Game_player_id_2 = "";
    public static String Game_player_name_1 = "";
    public static String Game_player_name_2 = "";
    public static String Game_team_id_1 = "";
    public static String Game_team_id_2 = "";
    public static int Game_current_count = 1;
    public static TeamModel Game_team_1 = new TeamModel();
    public static TeamModel Game_team_2 = new TeamModel();
    public static PlayerModel Game_player_1 = new PlayerModel();
    public static PlayerModel Game_player_2 = new PlayerModel();

    public static void Clear() {
        Game_single_status = false;
        Game_count = 0;
        Game_player_count = 1;
        Game_current_count = 1;
        Game_id = "";
        Game_name = "";
        Game_player_id_1 = "";
        Game_player_id_2 = "";
        Game_player_name_1 = "";
        Game_player_name_2 = "";
        Game_team_id_1 = "";
        Game_team_id_2 = "";
        Game_team_1 = new TeamModel();
        Game_team_2 = new TeamModel();
        Game_player_1 = new PlayerModel();
        Game_player_2 = new PlayerModel();
    }
}
