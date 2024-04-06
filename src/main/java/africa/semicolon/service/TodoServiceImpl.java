package africa.semicolon.service;

import africa.semicolon.data.model.Todo;
import africa.semicolon.data.repository.TodoRepository;
import africa.semicolon.dto.request.CreateTaskRequest;
import africa.semicolon.dto.response.CreateTaskResponse;
import africa.semicolon.exceptions.AuthorDoesNotExist;
import africa.semicolon.exceptions.InvalidTitleException;
import africa.semicolon.exceptions.TitleAlreadyExistException;
import africa.semicolon.util.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TodoServiceImpl implements TodoService {

    @Autowired
    TodoRepository todoRepository;
    @Override
    public CreateTaskResponse createTask(CreateTaskRequest createTaskRequest) {


            if (ifTitleAlreadyExist(createTaskRequest.getTitle())){
                throw new TitleAlreadyExistException("this title already exist");
            }
            Todo todo = Mapper.mapper(createTaskRequest);
            Todo savedTodo = todoRepository.save(todo);

            CreateTaskResponse createTaskResponse = new CreateTaskResponse();
            createTaskResponse.setAuthor(savedTodo.getAuthor());
            createTaskResponse.setMessage("created successful");
            return createTaskResponse;

    }

    @Override
    public long getNumberOfTask() {
        return todoRepository.count();
    }

    @Override
    public void delete(String title) {
        if (ifTitleAlreadyExist(title)){
            throw new InvalidTitleException("this title does not exist");
        }
        todoRepository.deleteByTitle(title);
    }

    private boolean isTitlePresent(String title){
        return todoRepository.findByTitle(title) != null;
    }

    @Override
    public Todo findByAuthor(String author) {
//        if (ifTitleAlreadyExist(author)){
//            throw new AuthorDoesNotExist("this author does not exist");
//        }
        return todoRepository.findByAuthor(author);
    }

    private boolean isAuthorPresent(String author){
        return todoRepository.findByTitle(author) != null;
    }

    @Override
    public void deleteAll() {
        todoRepository.deleteAll();


    }

    private boolean ifTitleAlreadyExist(String title){return todoRepository.findByAuthor(title) != null; }
}
