import { Meta, moduleMetadata, Story } from '@storybook/angular';

import { DocumentosComponent } from './documentos.component';
import { DocumentosModule } from './documentos.module';

type ComponentWithCustomControls = DocumentosComponent;

const meta: Meta<ComponentWithCustomControls> = {
  // title: 'Components/Documentos',
  component: DocumentosComponent,
  decorators: [
    moduleMetadata({
      imports: [DocumentosModule],
    }),
  ],
  parameters: {
    docs: { description: { component: `Documentos` } },
  },
  argTypes: {},
  args: {},
};
export default meta;

const Template: Story<ComponentWithCustomControls> = (
  args: ComponentWithCustomControls
) => ({ props: args });

export const Documentos = Template.bind({});
Documentos.args = {};
