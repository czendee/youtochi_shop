package com.tochi.RobotJUEGO;

/**
 * Created by 813743 on 20/09/2016.
 */

public class Constants {

	/*
	public  final String URL_GET_EMPLOYEE_INFO = "http://www8.brinkster.com/mexico/tol_despliega_libro.asp?clave=67&B1=Ver+Libro";
    public  final String URL_MONITOR_METRIC = "http://www.google.com/ig/calculator?hl=en&q=1USD%3D%3FINR";
    public  final String URL_GET_COMMAND_WWW8 = "http://www8.brinkster.com/mexico/tol_despliega_texto_cap.asp?B1=Leer+Libro&claveLibro=";
    public  final String  URL_PUT_COMMAND_WWW8 = "http://www8.brinkster.com/mexico/tol_despliega_libro.asp?clave=67&B1=Regresar";
    public  final String URL_AWB_HISTORY_INCAS = "http://www8.brinkster.com/mexico/tol_despliega_texto_cap.asp?B1=Leer+Libro&claveLibro=67";
    public  final String URL_AWB_EXPORT_HISTORY_INCAS = "http://www8.brinkster.com/mexico/tol_despliega_texto_cap.asp?B1=Leer+Libro&claveLibro=67";
 */

    public static final String TAG = "myApp";

    public  final static String GET_BUILDING_ALL_INFO = "00";


    public  final static String FILENAME_EMPLOYEE_CONFIG = "mobileEmployeeRecord.txt";

    public  final String URL_GET_BUILDING_ALL_INFO = "http://www8.brinkster.com/mexico/tol_despliega_texto_cap.asp?B1=Leer+Libro&claveLibro=";


    public  final String URL_ADD_EDIFICIO = "http://mexico.brinkster.net/tochi_crea_edificio_mobil.asp?operacion=guardar&";

    public  final String URL_ADD_RUTINA_ROBOT = "http://mexico.brinkster.net/tochi_crea_rutina_robot.asp?operacion=guardar&";

//robots
    public  final String URL_LIST_COMANDO_ROBOT = "http://peaceful-retreat-91246.herokuapp.com/api/comandosrobots";//el task lo consume como get

    public  final String URL_ADD_COMANDO_ROBOT = "http://peaceful-retreat-91246.herokuapp.com/api/comandosrobots";//el task lo consume como post, encviando el json :)

    public  final String URL_LIST_COMANDO_ROBOT_PENDIENTE_EJECUTAR = "http://peaceful-retreat-91246.herokuapp.com/api/comandosrobotspendientes";//el task lo consume como get

    public  final String URL_LIST_ROBOTS = "http://peaceful-retreat-91246.herokuapp.com/api/robots";

    public  final String URL_UPDATE_COMANDO_ROBOT_POST= "http://peaceful-retreat-91246.herokuapp.com/api/comandosrobotscambiaestatus02";

    public  final String URL_UPDATE_COMANDO_ROBOT_GET= "http://peaceful-retreat-91246.herokuapp.com/api/comandosrobotscambiaestatus";

    public  final String URL_UPDATE_ROBOTHISTORY_POSICION_GET= "http://peaceful-retreat-91246.herokuapp.com/api/robothistorycambiaposicion";

    public  final String URL_UPDATE_ROBOTHISTORY_POSICION_POST= "http://peaceful-retreat-91246.herokuapp.com/api/robothistorycambiaposicion02";

    public  final String URL_ADD_ROBOTHISTORY_POSICION_POST= "http://peaceful-retreat-91246.herokuapp.com/api/robothistory";

    public  final String URL_ADD_HABITACION = "http://mexico.brinkster.net/tochi_crea_habitacion_mobil.asp?operacion=guardar&";

    public  final String URL_GET_USER_ALL_INFO = "http://mexico.brinkster.net/tochi_get_user_admin_info_mobil.asp?operacion=consultar&usuario=";


    public  final static String GET_BUILDING_ALL_DATA = "10";



    public final  static String TEMPORAL_FILE_TEST_BUILDINGS_ALL_ONE_BULDING ="<html>"+
            "<body>"+
            "  una linea con datos como si fueran de un file de COLLECTORS" +
            "INFO" +
            "TABLE 1" +
            "tABLE 2" +
            "testBuiling01  "+
            "build[901|marquez sterling|df|AAA|image101][112|PARRILLA CASA AZUL|TABASCO|BBA|image112]ENDbuild"+
            "testBuiling02  "+
            "units[2050|BB|I605|carlos z|image1012050][2052|BB|I606|rogerz|image1012052][2055|BB|I607|michel|image1012055][2056|BB|I608|duran|image1012056]ENDunits"+
            "testBuiling02 2  "+
            "units[3042|CC|8|carlos|image1123042][3052|CC|2|anotnio|image1123052]ENDunits"+
            "testBuiling03  "+
            "buildunits[101|2050][101|2052][101|2055][101|2056][112|3042][112|3052]ENDbuildunits "+
            "</body>"+
            "</html>" ;





}

