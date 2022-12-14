# Прятки
Решение задачи Yandex Cup 2022.
<br>
<h3>Условие задачи</h3>
В детстве все наверное любили играть в прятки. Но сейчас информационный век и прятки можно сделать гораздо более технологичными. Вам предлагается в этом поучаствовать и написать приложение для этого. В приложении надо выбрать ведущего - он будет искать. Остальные - прячутся. "Прячущийся" телефон дает некоторую информацию о себе (когда спрятался) и может работать в двух режимах: скрытном - в этом режиме он подает сигнал по BLE о себе, и шумном - вместо BLE он выдает в динамик чириканье птиц. "Ищущий" телефон с помощью BLE приемника и микрофона получает сигналы о прячущихся и должен их найти. Интерфейс должен ему в этом помогать (при приближении по уровню сигнала BLE или по увеличению принимаемой громкости чириканья) подсказывать направление в котором искать "прячущегося".
<br>
<h3>Архитектура</h3>
<h5>Используемые технологии и основные библиотеки</h5>
<li><a href="https://developer.android.com/jetpack/compose">Jetpack Compose</a></li>
<li><a href="https://github.com/terrakok/Cicerone">Cicerone</a></li>
<li><a href="https://insert-koin.io/">Koin</a></li>
<li><a href="https://github.com/NordicSemiconductor/Android-BLE-Library">Nordic</a></li>
<br>
<h3>Описание решения</h3>
На экране главного меню пользователю предлагается выбрать, является ли он тем, кто прячется или, кто ищет. Пользователь, который ищет выступает в роли хоста и остальным игрокам необходимо установить с ним соединение. Когда нужно количество игроков соберется запускается таймер, который дает возможность игрокам спрятаться. После того, как все игроки спрячутся каждое из устройств будет подавать сигналы по BLE серверу, тем самым выдавая свое местоположение. У игрока, который ищет, на экран нарисована карта с примерным расположением других учасников. При приближении к игроку, его телефон начнет "чирикать". В свою очередь ведущемув этот момент будет предолжено отсканировать QR-код пользователя, к которому он приблизился. После сканирования QR кода пользователь, которого нашли, будет отключен от игры.
Операции подключения и поддержания связи между клиентами и хостом запускаются на Foreground Service, чтобы избежать ситуации, когда после выхода Activity из фокуса игрок перестает обновлять информацию о себе.
