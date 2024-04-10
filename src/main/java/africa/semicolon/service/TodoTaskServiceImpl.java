package africa.semicolon.service;

import africa.semicolon.data.model.TodoTask;
import africa.semicolon.data.repository.TodoRepository;
import africa.semicolon.dto.request.CreateTaskRequest;
import africa.semicolon.dto.request.UpdateTaskRequest;
import africa.semicolon.dto.response.CompleteTaskResponse;
import africa.semicolon.dto.response.CreateTaskResponse;
import africa.semicolon.dto.response.IncompleteTaskResponse;
import africa.semicolon.dto.response.UpdateTaskResponse;
import africa.semicolon.exceptions.InvalidTitleException;
import africa.semicolon.exceptions.TitleAlreadyExistException;
import africa.semicolon.util.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

import static africa.semicolon.data.model.Status.COMPLETE;
import static africa.semicolon.data.model.Status.INCOMPLETE;

@Service
public class TodoTaskServiceImpl implements TodoTaskService {

    @Autowired
    TodoRepository todoRepository;
    @Override
    public CreateTaskResponse createTask(CreateTaskRequest createTaskRequest) {


            if (ifTitleAlreadyExist(createTaskRequest.getTitle())){
                throw new TitleAlreadyExistException("this title already exist");
            }
            TodoTask todo = Mapper.mapper(createTaskRequest);
            TodoTask savedTodo = todoRepository.save(todo);

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
    public TodoTask findByAuthor(String author) {
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

    @Override
    public UpdateTaskResponse update(UpdateTaskRequest updateTaskRequest) {
        if (ifTitleAlreadyExist(updateTaskRequest.getTitle())){
            throw new TitleAlreadyExistException("this title already exist");
        }
        TodoTask todoTask = Mapper.updateMapper(updateTaskRequest);
        TodoTask savedTodo = todoRepository.save(todoTask);

        UpdateTaskResponse updateTaskResponse = new UpdateTaskResponse();
        updateTaskResponse.setAuthor(savedTodo.getAuthor());
        updateTaskResponse.setMessage("updated successful");
        return updateTaskResponse;

    }



    @Override
    public List<TodoTask> findCompletedTasks() {
        return todoRepository.findAll().stream().filter(todoTask -> todoTask.getStatus() == COMPLETE).collect(Collectors.toList());


    }

    @Override
    public List<TodoTask> findInCompletedTasks() {
        return todoRepository.findAll().stream().filter(todoTask -> todoTask.getStatus() == INCOMPLETE).collect(Collectors.toList());
    }

    @Override
    public CompleteTaskResponse completeTask(CreateTaskRequest createTaskRequest) {
        TodoTask todoTask = Mapper.completeMapper(createTaskRequest);
        TodoTask completed = todoRepository.save(todoTask);

        CompleteTaskResponse completeTaskResponse = new CompleteTaskResponse();
        completeTaskResponse.setStatus(COMPLETE);
        completeTaskResponse.setAuthor(completed.getAuthor());
        completeTaskResponse.setMessage("this task is completed");
        return completeTaskResponse;
    }

    private boolean isTaskComplete(CreateTaskRequest createTaskRequest){
        return todoRepository.findByAuthor(createTaskRequest.getAuthor()) != null;
    }

    @Override
    public IncompleteTaskResponse incompleteTask(CreateTaskRequest incompleteTaskRequest) {

    }

    private boolean ifTitleAlreadyExist(String title){return todoRepository.findByAuthor(title) != null; }
}
