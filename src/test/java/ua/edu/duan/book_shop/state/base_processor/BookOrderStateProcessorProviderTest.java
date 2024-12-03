package ua.edu.duan.book_shop.state.base_processor;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import ua.edu.duan.book_shop.enums.OrderState;
import ua.edu.duan.book_shop.state.*;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertInstanceOf;

@SpringBootTest
public class BookOrderStateProcessorProviderTest {
    @Mock
    private NewOrderStateProcessor newOrderStateProcessor;
    @Mock
    private ProcessingOrderStateProcessor processingOrderStateProcessor;
    @Mock
    private DeliveredOrderStateProcessor deliveredOrderStateProcessor;
    @Mock
    private CanceledOrderStateProcessor canceledOrderStateProcessor;

    @InjectMocks
    private BookOrderStateProcessorProvider bookOrderStateProcessorProvider;

    @ParameterizedTest
    @MethodSource("getProcessorModel")
    public void testGetStateProcessor(ProcessorModel model) {
        BaseOrderStateProcessor processor = bookOrderStateProcessorProvider.getStateProcessor(model.state());
        assertInstanceOf(model.expectedClass(), processor);
    }

    private static Stream<ProcessorModel> getProcessorModel() {
        return Stream.of(
                new ProcessorModel(OrderState.NEW, NewOrderStateProcessor.class),
                new ProcessorModel(OrderState.PROCESSING, ProcessingOrderStateProcessor.class),
                new ProcessorModel(OrderState.DELIVERED, DeliveredOrderStateProcessor.class),
                new ProcessorModel(OrderState.CANCELED, CanceledOrderStateProcessor.class)
        );
    }
}
