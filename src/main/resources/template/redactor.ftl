<!DOCTYPE html>
<html lang="ru">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Изменение страинцы</title>
</head>
<body>

<form action="/oris_semectrovka_01_war_exploded/registration" method="post">

    <h2>Изменение пользователя</h2>


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

    <input type="submit" value="Готово">
    <a href="index">Вернуться обратно</a>
</form>
