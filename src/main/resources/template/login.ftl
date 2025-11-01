
<!DOCTYPE html>
<html lang="ru">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Регистрация и Вход</title>
</head>
<body>
<!-- Форма регистрации -->
<form id="registrationForm" action="/oris_semectrovka_01_war_exploded/registration" method="post">

    <h2>Регистрация пользователя</h2>

    <div>
        <label for="login">Логин:</label>
        <input type="text" id="login" name="login" required>
    </div>

    <div>
        <label for="password">Пароль:</label>
        <input type="password" id="password" name="password" required>
    </div>

    <div>
        <label for="name">Имя:</label>
        <input type="text" id="name" name="name" required>
    </div>

    <div>
        <label for="email">Email:</label>
        <input type="email" id="email" name="email" required>
    </div>

    <button type="submit">Зарегистрироваться</button>

    <!-- Блок для вывода ошибок регистрации -->
    <div id="message" style="color: red; margin-top: 10px;"></div>
</form>

<!-- Форма входа -->
<form action="/oris_semectrovka_01_war_exploded/usercheck" method="post">

    <h2>Вход в систему</h2>

    <div>
        <label for="username">Логин:</label>
        <input type="text" id="login" name="login" required>
    </div>

    <div>
        <label for="login-password">Пароль:</label>
        <input type="password" id="login-password" name="password" required>
    </div>

    <input type="submit" value="Вход">

    <!-- Блок для вывода ошибок входа -->
    <div id="message" style="color: red; margin-top: 10px;"></div>
</form>

</body>
</html>