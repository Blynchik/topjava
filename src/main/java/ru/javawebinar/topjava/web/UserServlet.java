package ru.javawebinar.topjava.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.repository.MealRepository;
import ru.javawebinar.topjava.repository.UserRepository;
import ru.javawebinar.topjava.repository.inmemory.InMemoryMealRepository;
import ru.javawebinar.topjava.repository.inmemory.InMemoryUserRepository;
import ru.javawebinar.topjava.to.MealTo;
import ru.javawebinar.topjava.util.MealsUtil;
import ru.javawebinar.topjava.util.UsersUtil;
import ru.javawebinar.topjava.util.ValidationUtil;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Objects;

import static org.slf4j.LoggerFactory.getLogger;

public class UserServlet extends HttpServlet {
    private static final Logger log = LoggerFactory.getLogger(UserServlet.class);

    private UserRepository repository;

    @Override
    public void init() {
        repository = new InMemoryUserRepository();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");

        int id;
        switch (action == null ? "all" : action) {
            case "delete":
                ValidationUtil.checkNotFoundWithId(repository.get(getId(request)),getId(request));
                id = getId(request);
                log.info("Delete id={}", id);
                repository.delete(id);
                response.sendRedirect("users");
                break;
            case "create":
            case "update":
                final User user = "create".equals(action) ?
                        new User(null, "", "", "") :
                        repository.get(getId(request));
                request.setAttribute("user", user);
                request.getRequestDispatcher("/userForm.jsp").forward(request, response);
                break;
            case "all":
            default:
                log.info("getAll");
                request.setAttribute("users",
                        UsersUtil.getTos(repository.getAll()));
                request.getRequestDispatcher("/users.jsp").forward(request, response);
                break;
        }
    }
        @Override
        protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
        request.setCharacterEncoding("UTF-8");
        String id = request.getParameter("id");

        User user = new User(id.isEmpty()? null: Integer.valueOf(id),
                request.getParameter("name"),
                request.getParameter("email"),
                request.getParameter("password"));

        log.info(user.isNew()? "Create {}" : "Update {}", user);
        repository.save(user);
        response.sendRedirect("users");
        }

    private int getId(HttpServletRequest request){
        String paramId = Objects.requireNonNull(request.getParameter("id"));
        return Integer.parseInt(paramId);
    }
}
