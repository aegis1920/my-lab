package com.bingbong.effectivejava.item27;

import java.util.HashSet;
import java.util.Set;

class WarningTest {

    public static void main(String[] args) {

    }

    @SuppressWarnings("unchecked")
    class ClassWarning {

        public Set<String> hello() {
            return new HashSet();
        }
    }

    class MethodWarning {

        public Set<String> hello() {
            return new HashSet();
        }
    }

    class FieldWarning {

        @SuppressWarnings("unchecked")
        Set<String> set = new HashSet();
    }
}
