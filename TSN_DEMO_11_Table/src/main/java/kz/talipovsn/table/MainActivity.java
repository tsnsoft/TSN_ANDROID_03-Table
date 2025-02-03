package kz.talipovsn.table;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.view.Gravity;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    // Количество строк в таблице
    private final int NUM_ROWS = 4;
    // Количество столбцов в таблице
    private final int NUM_COLS = 5;
    // Массив для работы с данными таблицы
    int[][] arr = new int[NUM_ROWS][NUM_COLS];
    // Объявляем табличную разметку
    TableLayout tableLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tableLayout = findViewById(R.id.tableLayout); // Получаем табличную разметку по id

        // Заполняем массив нулями
        for (int i = 0; i < NUM_ROWS; i++) { // Цикл по строкам массива
            for (int j = 0; j < NUM_COLS; j++) { // Цикл по столбцам массива
                arr[i][j] = 0; // Заполняем массив нулями
            }
        }

        // Параметры разметки таблицы
        tableLayout.setLayoutParams(new TableLayout.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
        )); // Устанавливаем параметры разметки таблицы по ширине и высоте на содержимое

        tableLayout.setStretchAllColumns(true); // Растягиваем все столбцы таблицы по содержимому

        // Создание таблицы
        createTable(NUM_ROWS, NUM_COLS, arr);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        // Сохраняем данные при повороте экрана
        for (int i = 0; i < NUM_ROWS; i++) { // Цикл по строкам массива
            outState.putIntArray("TABLE_ROW_" + i, arr[i]); // Сохраняем строку массива в буфер
        }

        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        // Возвращаем данные в массив
        for (int i = 0; i < NUM_ROWS; i++) { // Цикл по строкам массива
            arr[i] = savedInstanceState.getIntArray("TABLE_ROW_" + i); // Считываем строку массива из буфера
        }
        // Пересоздаем таблицу
        createTable(NUM_ROWS, NUM_COLS, arr);
    }

    // Метод создания таблицы. Входные значения: количество строк, количество столбцов, двумерный массив с данными.
    public void createTable(int rows, int columns, int[][] arr) {
        tableLayout.removeAllViews(); // Удаляем предыдущие записи при пересоздании

        for (int i = 0; i < rows; i++) { // Цикл по строкам массива
            // Создание строки
            TableRow tableRow = new TableRow(this); // Объявление объекта строки таблицы
            LayoutParams params = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT); // Параметры строки таблицы
            tableRow.setLayoutParams(params); // Устанавливаем параметры строки
            TableRow.LayoutParams param = new TableRow.LayoutParams(); // Параметры ячейки

            // Создание столбцов в строке
            for (int j = 0; j < columns; j++) { // Цикл по столбцам массива
                EditText editText = new EditText(this); // Объявление объекта ячейки таблицы
                editText.setText(String.valueOf(arr[i][j])); // Заполняем ячейку таблицы данными из массива
                editText.setLayoutParams(param); // Устанавливаем параметры ячейки таблицы
                editText.setTextSize(18); // Размер текста
                editText.setGravity(Gravity.CENTER); // Выравнивание текста по центру
                editText.setBackgroundResource(R.drawable.back); // Фон ячейки таблицы (рамка)
                editText.setPadding(5, 5, 5, 5); // Отступы внутри ячейки
                tableRow.addView(editText, j); // Добавляем компонент в строку таблицы
            }
            tableLayout.addView(tableRow, i); // Добавляем строку в таблицу
        }
    }

    // Заполняем массив числами от 0 до 100
    public void fillRand(View v) {
        for (int i = 0; i < NUM_ROWS; i++) { // Цикл по строкам массива
            for (int j = 0; j < NUM_COLS; j++) { // Цикл по столбцам массива
                arr[i][j] = (int) Math.round(Math.random() * 100); // Заполняем ячейку таблицы случайным числом от 0 до 100
            }
        }
        // Пересоздание таблицы
        createTable(NUM_ROWS, NUM_COLS, arr);
    }

    // Заполняем массив нулями
    public void fillZero(View v) {
        for (int i = 0; i < NUM_ROWS; i++) { // Цикл по строкам массива
            for (int j = 0; j < NUM_COLS; j++) { // Цикл по столбцам массива
                arr[i][j] = 0; // Заполняем ячейку таблицы нулем
            }
        }
        // Пересоздание таблицы
        createTable(NUM_ROWS, NUM_COLS, arr);
    }

    // Решение задачи
    public void solveArr(View v) {

        // Записываем в массив значения из таблицы
        for (int i = 0; i < tableLayout.getChildCount(); i++) { // Цикл по строкам таблицы
            TableRow row = (TableRow) tableLayout.getChildAt(i); // Получаем строку таблицы
            for (int j = 0; j < row.getChildCount(); j++) { // Цикл по столбцам таблицы
                EditText editText = (EditText) row.getChildAt(j); // Получаем ячейку таблицы
                try {
                    arr[i][j] = Integer.parseInt(editText.getText().toString()); // Записываем значение из ячейки таблицы в массив
                } catch (Exception e) {
                    // Вывод сообщения при исключении (неправильно введенные данные)
                    Toast toast = Toast.makeText(getApplicationContext(), "Неправильно введенны данные!", Toast.LENGTH_SHORT);
                    toast.show(); // Вывод сообщения
                }
            }
        }

        // Находим максмимальный элемент таблицы и номер строки, в которой он находится
        int max = arr[0][0], maxi = 0;
        for (int i = 0; i < NUM_ROWS; i++) { // Цикл по строкам массива
            for (int j = 0; j < NUM_COLS; j++) { // Цикл по столбцам массива
                int cl = arr[i][j]; // Текущий элемент
                if (cl > max) { // Если текущий элемент больше максимального
                    max = cl; // Записываем текущий элемент в максимальный
                    maxi = i; // Записываем номер строки
                }
            }
        }

        // Если максмимальный элемент в последней строке, то увеличиваем все элементы первого столбца в 2 раза
        if (maxi == 3) {
            for (int i = 0; i < NUM_ROWS; i++) { // Цикл по строкам массива
                arr[i][0] *= 2; // Увеличиваем элемент первого столбца в 2 раза
            }
            new Toast(getApplicationContext()).makeText(getApplicationContext(),
                    "Задание выполнено!", Toast.LENGTH_SHORT).show(); // Вывод сообщения
        } else {
            // Если максмимальный элемент не в последней строке
            new Toast(getApplicationContext()).makeText(getApplicationContext(),
                    "Максимальный элемент не в последней строке!", Toast.LENGTH_SHORT).show();
        }

        // Пересоздаем таблицу
        createTable(NUM_ROWS, NUM_COLS, arr);
    }

}
