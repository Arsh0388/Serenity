package todomvc.features;

import net.serenitybdd.core.Serenity;
import net.serenitybdd.junit5.SerenityJUnit5Extension;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.actions.Enter;
import net.serenitybdd.screenplay.actions.Open;
import net.serenitybdd.screenplay.annotations.CastMember;
import net.serenitybdd.screenplay.questions.Text;
import net.serenitybdd.screenplay.waits.WaitUntil;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.openqa.selenium.Keys;

import static net.serenitybdd.screenplay.matchers.WebElementStateMatchers.isPresent;
import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SerenityJUnit5Extension.class)
class AddNewTodos {

    @CastMember(name = "Toby")
    Actor toby;

    @Test
    @DisplayName("Add a todo item to an empty list")
    void addToEmptyList() {
        toby.attemptsTo(
                // Use the working TodoMVC Angular URL from official Serenity examples
                Open.url("https://todomvc.com/examples/angular/dist/browser/#/all"),
                // Wait for the input field to be present before interacting
                WaitUntil.the(".new-todo", isPresent()).forNoMoreThan(10).seconds(),
                Enter.theValue("Buy some milk").into(".new-todo").thenHit(Keys.RETURN)
        );

        // Wait a moment for the todo to appear in the list
        WaitUntil.the(".todo-list li", isPresent()).forNoMoreThan(5).seconds();

        var todos = toby.asksFor(Text.ofEach(".todo-list li"));
        Serenity.reportThat("The keyword should appear in the sidebar heading",
                () -> assertThat(todos).containsExactly("Buy some milk")
        );

    }
}