package com.clafaleo.reencaminhaqui.entities.enums;

public enum StatusRota {
    ATIVA(1), PAUSADA(2);

    private int code;

    private StatusRota(int code) {
        this.code = code;
    }

    public int getCode() {
        return code;
    }

    public  static StatusRota valueOf(int code) {
        for (StatusRota value : StatusRota.values()) {
            if (value.getCode() == code) {
                return value;
            }
        }
        throw new IllegalArgumentException("Invalid StatusCanal code");
    }

}
