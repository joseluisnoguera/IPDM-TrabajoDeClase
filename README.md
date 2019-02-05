# IPDM-TrabajoDeClase

En el presente se encuentran resueltos los incisos solicitados para la cursada de Introducción a la programación de móviles del año 2018.


Ejercicio 1:
Desarrollar una aplicación que al apretar un botón:
● Imprima en el logcat el mensaje “El usuario apretó el botón”.
● Se muestre un Toast con el mensaje “Hola mundo!”


Ejercicio 2:
Desarrollar una aplicación con dos Activity.
● La primera de ellas tendrá:
○ Un botón que permita obtener los datos de un contacto del dispositivo, y
mostrarlos en un un TextView.
○ Un botón que lance la segunda Activity, que va a devolver el resultado de un
multiplicación para que se muestre en un TextView.
● La segunda Activity tendrá:
○ 2 TextView que permitan el ingreso de dos valores.
○ 1 botón que calcule el producto de los valores ingresados anteriormente.
○ La Activity debe finalizar retornado el resultado de la multiplicación a la
Activity que la invocó.
En ambos casos, se debe resguardar y restaurar los estados de las Activity para que al girar
la pantalla del dispositivo no se pierdan los valores de los TextView.


Ejercicio 3:
Desarrollar un segundero con tres botones y un texto, donde el primer botón sea un
“comenzar” y los segundos comiencen a acumularse, luego un botón “parar” que detenga la
cuenta, y por último un botón “resetear” que permita resetear el acumulador. El proceso de
acumulación de segundos deberá realizarse con una AsyncTask. Por último, si la aplicación
es cerrada y vuelta abrir, mediante la utilización de SharedPreferences el contador debe
estar en el último estado dejado por el usuario.


Ejercicio 4
Se requieren implementar dos Background Servicios, el primero que extienda de
IntentService y el segundo que extienda de Service. La lógica de negocio para estos
servicios será la misma, recibirán en un intent un número de iteración, se domirá el thread
por 3 segundos y luego se enviará un broadcast local con el número de iteración
recuperado del Intent recibido.
Se deberá implementar una activity con 2 botones, que realizarán 4 llamadas a los servicio
cada uno, y 2 campos de texto que mostran la iteración reciba mediante Broadcast receiver
para cada uno de los servicios.


Ejercicio 5
Se requiere implementar un Bound Service que permita conexión desde otros
procesos/aplicaciones. Este servicio retornará un número aleatorio entre 0 y 100, el cual
será enviado y mostrado en la activity del ejercicio anterior, para lo cual habrá que agregar
un nuevo campo de texto.
Se deberá realizar una nueva aplicación que se enlace a este servicio y realice la llamada.
