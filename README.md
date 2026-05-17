# Actividad 1: Compilación a Código Máquina vs Bytecode (C, Java y Python)

Este repositorio contiene la solución completa para la **Actividad de Compilación y Reversibilidad**, que explora los niveles de traducción, abstracción y reversibilidad en diferentes arquitecturas de compilación e interpretación.

---

## 📂 Estructura del Proyecto

*   `holamundo.c` - Código fuente en C.
*   `HolaMundo.java` - Código fuente en Java.
*   `HolaMundo.py` - Código fuente en Python.
*   `evidencias/` - Directorio que aloja los artefactos resultantes del proceso de compilación, bytecode y descompilación:
    *   `c/objdump_main.txt` - Ensamblador desensamblado del binario de C (`main`).
    *   `java/javap_bytecode.txt` - Bytecode generado de la clase Java.
    *   `python/dis_bytecode.txt` - Bytecode desensamblado del script de Python.
    *   `python/pycdc_reconstruido.py` - Código fuente de Python decompilado con `pycdc` a partir del `.pyc`.
    *   `informe_compilacion.md` - **Informe técnico completo** detallando las fases 1, 2, 3 y las respuestas técnicas rigurosas a la Fase 4 (Reflexión).
    *   `ghidra/` - Carpeta designada para guardar las capturas de descompilación con Ghidra local (`c_decompile.png` y `java_decompile.png`).

---

## ⚡ Comandos de Compilación Utilizados

### Fase 1: C
```bash
# Compilar desactivando PIE/PIC para lectura estática estable y deshabilitando built-ins para printf
gcc holamundo.c -o holamundo -no-pie -fno-pic -fno-builtin

# Desensamblar la función main
objdump -d -M intel holamundo | grep -A 20 "<main>:" > evidencias/c/objdump_main.txt
```

### Fase 2: Java
```bash
# Compilar a bytecode de Java (.class)
javac HolaMundo.java

# Extraer el bytecode estructurado
javap -c HolaMundo > evidencias/java/javap_bytecode.txt
```

### Fase 3: Python
```bash
# Desensamblar con el módulo dis de la biblioteca estándar
python3 -m dis HolaMundo.py > evidencias/python/dis_bytecode.txt

# Compilar a archivo bytecode optimizado (.pyc)
python3 -m compileall .

# Decompilar el .pyc con pycdc (Decompyle++)
./pycdc/build/pycdc __pycache__/HolaMundo.cpython-312.pyc > evidencias/python/pycdc_reconstruido.py
```

---

## 📝 Informe Completo

El análisis detallado con las explicaciones del marco de pila, el constant pool, las instrucciones de bajo nivel de la JVM y CPython, y el análisis de reversibilidad y seguridad se encuentra en:

👉 [**Leer el Informe de Compilación (evidencias/informe_compilacion.md)**](file:///c:/Users/ruben/Proyectos/Antigravity/UEM_CC.LL.FF_ACT1_Compilacion/evidencias/informe_compilacion.md)

---
*Desarrollado y validado en el entorno Antigravity.*
