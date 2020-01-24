package br.com.url.shortener.constants;

public enum Letter {

	A('0'), B('1'), C('2'), D('3'), E('4'), F('5'), G('6'), H('7'), I('8'), J('9');

	private char key;
	
	Letter(char key){
		this.key = key;
	}
	
    public static String searchEnum(char key) {
        for (Letter each : Letter.values()) {
            if (each.key == key) {
                return each.name().toLowerCase();
            }
        }
        return Letter.A.name().toLowerCase();
    }
}
